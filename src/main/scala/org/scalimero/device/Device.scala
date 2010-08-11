package org.scalimero.device

import scala.actors.Actor
import scala.actors.Actor._

import org.scalimero.device.dtype._
import org.scalimero.dsl._
import org.scalimero.util._

import tuwien.auto.calimero._  
import tuwien.auto.calimero.datapoint._
import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.process._
import tuwien.auto.calimero.GroupAddress

trait TDevice

trait TCommandDevice[T] extends TDevice {
  def send(value: T): Unit
}

trait TStateDevice[T] extends TDevice {
  def read(): T
}

abstract class Device[DataPointValueType <: DPValue[PrimitiveType], PrimitiveType](destAddr: GroupAddress, tt: TranslatorType, dpt: DPType[DataPointValueType, PrimitiveType], name: String, net: Network)
  extends Actor with EventHelper[PrimitiveType] with WriteCallbackHelper[PrimitiveType]{

  val dp: Datapoint

  start
  net.subscribe(this, List(destAddr))
  
  def detach() = net.unsubscribe(this)
  def send(dpvalue: DataPointValueType) = net.send(dp, dpt.translate(dpvalue.value))

  override def act() {
    loop{
      react {
        case p: ProcessEvent => {
          val value : PrimitiveType= dpt.translate(dpt.translate(p.getASDU))
          callEvents(value)
          callWrites(value)
        }
        case Subscribe(event, callback) => reply { super.subscribe(event)(callback) }
        case UnSubscribe(callback) => super.unsubscribe(callback)
        case WSubscribe(callback) => reply { super.subscribe(callback) }
        case WUnsubscribe(callback) => super.unsubscribe(callback)
        case _ => println("A message has arrived Sir!")
      }
    }
  }

  override def subscribe(event: Any) (callback: => Unit) = {
    this !? Subscribe(event, callback _) match {
      case e: EventCallback => e
      case _ => throw new Exception("This happens all the time(hahaha)!")
    }
  }
  override def unsubscribe(callback: EventCallback) {
    this ! UnSubscribe(callback)
  }

  override def subscribe(callback : PrimitiveType => Unit) = {
    this !? WSubscribe(callback) match {
      case w: WriteCallback => w
      case _ => throw new Exception("This happens all the time(hahaha)!")
    }
  }
  override def unsubscribe(callback: WriteCallback) {
    this ! WUnsubscribe(callback)
  }

  case class Subscribe(event: Any, callback: () => Unit)
  case class UnSubscribe(callback: EventCallback)
  
  case class WSubscribe(callback : PrimitiveType => Unit)
  case class WUnsubscribe(callback : WriteCallback)
}

object Device {
  def apply[DataPointValueType <: DPValue[PrimitiveType], PrimitiveType](destAddress:GroupAddress, tt: TranslatorType, dpt: DPType[DataPointValueType, PrimitiveType], name: String = "", net: Network = Network.default) =
    new StateDevice(destAddress, tt, dpt, name, net)
}

class CommandDevice[DataPointValueType <: DPValue[PrimitiveType], PrimitiveType](destAddress:GroupAddress, tt: TranslatorType, dpt: DPType[DataPointValueType, PrimitiveType], name: String = "", net: Network = Network.default) 
  extends Device(destAddress, tt, dpt, name, net){
  override val dp = new CommandDP(destAddress, name, tt.mainNumber, dpt.id)
  
}

class StateDevice[DataPointValueType <: DPValue[PrimitiveType], PrimitiveType](destAddress:GroupAddress, tt: TranslatorType, dpt: DPType[DataPointValueType, PrimitiveType], name: String = "", net: Network = Network.default) 
  extends Device(destAddress, tt, dpt, name, net){
  override val dp = new StateDP(destAddress, name, tt.mainNumber, dpt.id)

  def read(): PrimitiveType = dpt.translate(net.read(dp))
}