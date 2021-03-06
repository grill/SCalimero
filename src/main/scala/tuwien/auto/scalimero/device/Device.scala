package tuwien.auto.scalimero.device

import scala.actors.Actor
import scala.actors.Actor._

import tuwien.auto.scalimero.device.dtype._
//import tuwien.auto.scalimero.dsl._
import tuwien.auto.scalimero.util._
import tuwien.auto.scalimero.connection._

import tuwien.auto.calimero._  
import tuwien.auto.calimero.datapoint._
import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.process._
import tuwien.auto.calimero.GroupAddress

trait TDevice
trait TCommandDevice[DataPointValueType <: DPValue[PrimitiveType], PrimitiveType]
  extends TDevice {
  def send(value: DataPointValueType): Unit
}

trait TStateDevice[PrimitiveType] extends TDevice {
  def read(): PrimitiveType
}

class SimpleDevice[DataPointValueType <: DPValue[PrimitiveType], PrimitiveType]
  (destAddr: GroupAddress, val dpt: DPType[DataPointValueType, PrimitiveType],
  name: String = "", net: Network = Network.default) extends TCommandDevice[DataPointValueType, PrimitiveType]
  with TStateDevice[PrimitiveType]{
  val dp = new StateDP(destAddr, name, dpt.mainNumber, dpt.id)

  def read(): PrimitiveType = dpt.translate(net.read(dp))
  def readOption(): Option[PrimitiveType] = try { Some(read) } catch { case e => None}
  def readRequest() = net.readRequest(destAddr)

  def send(dpvalue: DataPointValueType):Unit = net.send(dp, dpt.translate(dpvalue.value))
  def write(pvalue: PrimitiveType) = net.send(dp, dpt.translate(pvalue))
  def write(pvalue: Array[Byte]) = net.send(dp, dpt.translate(pvalue))
  def write(pvalue: String) = net.send(dp, pvalue)
}

abstract class Device[DataPointValueType <: DPValue[PrimitiveType], PrimitiveType]
  (destAddr: GroupAddress, dpt: DPType[DataPointValueType, PrimitiveType],
  name: String, net: Network) extends Actor with EventHelper[PrimitiveType] with WriteCallbackHelper[PrimitiveType]
  with TCommandDevice[DataPointValueType, PrimitiveType]{

  val dp: Datapoint
  
  start
  net.subscribe(this, List(destAddr))
  
  def detach() = net.unsubscribe(this)
  def send(dpvalue: DataPointValueType) = net.send(dp, dpt.translate(dpvalue.value))
  private[device] def write(pvalue: PrimitiveType) = net.send(dp, dpt.translate(pvalue))

  override def act() {
    loop{
      react {
        case pe: ProcessEvent => {
          this ! WriteEvent(dpt.translate(pe.getASDU), pe.getDestination)
        }
        case we: WriteEvent => {
          val value : PrimitiveType= dpt.translate(we.value)
          callEvents(value)
          callWrites(value)
        }
        case Subscribe(event, callback) => reply { super.eventSubscribe(event){callback()} }
        case UnSubscribe(callback) => super.eventUnsubscribe(callback)
        case WSubscribe(callback) => reply { super.writeSubscribe(callback) }
        case WUnsubscribe(callback) => super.writeUnsubscribe(callback)
        case _ => println("A message has arrived Sir!")
      }
    }
  }

  override def eventSubscribe(event: Any) (callback: => Unit) = {
    this !? Subscribe(event, callback _) match {
      case e: EventCallback => e
      case _ => throw new Exception("This happens all the time(hahaha)!")
    }
  }
  override def eventUnsubscribe(callback: EventCallback) {
    this ! UnSubscribe(callback)
  }

  override def writeSubscribe(callback : PrimitiveType => Unit) = {
    this !? WSubscribe(callback) match {
      case w: WriteCallback => w
      case _ => throw new Exception("This happens all the time(hahaha)!")
    }
  }
  override def writeUnsubscribe(callback: WriteCallback) {
    this ! WUnsubscribe(callback)
  }

  case class Subscribe(event: Any, callback: () => Unit)
  case class UnSubscribe(callback: EventCallback)
  
  case class WSubscribe(callback : PrimitiveType => Unit)
  case class WUnsubscribe(callback : WriteCallback)
}

object Device {
  def apply[DataPointValueType <: DPValue[PrimitiveType], PrimitiveType]
    (destAddress:GroupAddress, dpt: DPType[DataPointValueType, PrimitiveType],
    name: String = "", net: Network = Network.default) = new StateDevice(destAddress, dpt, name, net)
}

class CommandDevice[DataPointValueType <: DPValue[PrimitiveType], PrimitiveType]
  (destAddress:GroupAddress, dpt: DPType[DataPointValueType, PrimitiveType],
  name: String = "", net: Network = Network.default) 
  extends Device(destAddress, dpt, name, net){
  
  override val dp = new CommandDP(destAddress, name, dpt.mainNumber, dpt.id)
}

class StateDevice[DataPointValueType <: DPValue[PrimitiveType], PrimitiveType]
  (destAddress:GroupAddress, dpt: DPType[DataPointValueType, PrimitiveType],
  name: String = "", net: Network = Network.default) 
  extends Device(destAddress, dpt, name, net) with TStateDevice[PrimitiveType]{
  
  override val dp = new StateDP(destAddress, name, dpt.mainNumber, dpt.id)

  def read(): PrimitiveType = dpt.translate(net.read(dp))
  def readOption(): Option[PrimitiveType] = try { Some(read) } catch { case e => None}
}
