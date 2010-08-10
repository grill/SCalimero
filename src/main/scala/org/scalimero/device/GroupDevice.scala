package org.scalimero.device

import org.scalimero.device.dtype._
import org.scalimero.dsl._
import org.scalimero.util._
import org.scalimero.device._

import scala.collection.mutable._

import tuwien.auto.calimero.GroupAddress

class GroupDevice[DataPointValueType, PrimitveType](dpt: DPType[DataPointValueType, PrimitveType]) 
  extends TCommandDevice[T] with HashSet[Device[DataPointValueType, PrimitveType]]{
  var proxyFun = (value: PrimitveType) => value
  var master: StateDevice[DataPointType, PrimitveType] = null
  
  def this(destAddress: GroupAddress, tt: TranslatorType, dpt: DPType[T], name: String = "", net: Network = Network.default){
    this(dpt)
    master = new StateDevice(destAddress, tt, dpt, name, net)
    master subscribe (value: T) => {
      val pvalue = proxyFun(value.value)
      this map {_ send value}
    }
  }

  def send(value: DataPointValueType) = {
    val pvalue = proxyFun(value.value)
    this map {_ send pvalue}
  }
  
  //This Function is called right before forwarding to all
  //registered Devices starts
  def addProxyFunction(fun: (T) => T) = proxyFun = fun
}