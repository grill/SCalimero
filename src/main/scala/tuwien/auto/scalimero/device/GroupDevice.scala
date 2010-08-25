package tuwien.auto.scalimero.device

import tuwien.auto.scalimero.device.dtype._
//import tuwien.auto.scalimero.dsl._
import tuwien.auto.scalimero.util._
import tuwien.auto.scalimero.device._
import tuwien.auto.scalimero.connection._

import scala.collection.mutable

import tuwien.auto.calimero.GroupAddress

class GroupDevice[DataPointValueType <: DPValue[PrimitiveType], PrimitiveType]
  extends mutable.HashSet[Device[DataPointValueType, PrimitiveType]] with TCommandDevice[DataPointValueType, PrimitiveType]{
  var proxyFun = (value: PrimitiveType) => value
  var master: StateDevice[DataPointValueType, PrimitiveType] = null

  def setMaster(d: StateDevice[DataPointValueType, PrimitiveType]){
    master = d
    master writeSubscribe { (value: PrimitiveType) =>
      val pvalue = proxyFun(value)
      this map {_ write pvalue} 
    }
  }
  
  def setMaster(destAddress: GroupAddress, dpt: DPType[DataPointValueType, PrimitiveType], name: String = "", net: Network = Network.default){
    setMaster(new StateDevice(destAddress, dpt, name, net))
  }

  override def send(value: DataPointValueType) = {
    val pvalue = proxyFun(value.value)
    this map {_ write pvalue}
  }

  //This Function is called right before forwarding to all
  //registered Devices starts
  def addProxyFunction(fun: (PrimitiveType) => PrimitiveType) = proxyFun = fun
}

object GroupDevice{
  def apply[DataPointValueType <: DPValue[PrimitiveType], PrimitiveType](d: Device[DataPointValueType, PrimitiveType]*){
    new GroupDevice[DataPointValueType, PrimitiveType] ++= d
  }
  def apply[DataPointValueType <: DPValue[PrimitiveType], PrimitiveType](destAddress: GroupAddress, dpt: DPType[DataPointValueType, PrimitiveType], name: String = "", net: Network = Network.default) {
    GroupDevice(new StateDevice(destAddress, dpt, name, net))
  }
  def apply[DataPointValueType <: DPValue[PrimitiveType], PrimitiveType]() = new GroupDevice[DataPointValueType, PrimitiveType]
}

class MultipleAddressDevice[DataPointValueType <: DPValue[PrimitiveType], PrimitiveType]
  (r: TStateDevice[PrimitiveType], w: TCommandDevice[DataPointValueType, PrimitiveType]) 
  extends TStateDevice[PrimitiveType] with TCommandDevice[DataPointValueType, PrimitiveType]{

  def this(raddr: GroupAddress, waddr: GroupAddress, dpt: DPType[DataPointValueType, PrimitiveType],
    name: String = "", net: Network = Network.default){
    this(new StateDevice(raddr, dpt, name, net), new CommandDevice(waddr, dpt, name, net))
  }

  override def send(value: DataPointValueType) = w send value
  override def read(): PrimitiveType = r.read
}
