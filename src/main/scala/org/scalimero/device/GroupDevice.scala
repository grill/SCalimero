package org.scalimero.device

import org.scalimero.device.dtype._
import org.scalimero.dsl._
import org.scalimero.util._
import org.scalimero.device._

import scala.collection.mutable._

import tuwien.auto.calimero.GroupAddress

class GroupDevice[DataPointValueType <: DPValue[PrimitveType], PrimitveType](dpt: DPType[DataPointValueType, PrimitveType])
  extends HashSet[Device[DataPointValueType, PrimitveType]]{
  var proxyFun = (value: PrimitveType) => value
  var master: StateDevice[DataPointValueType, PrimitveType] = null

  def this(destAddress: GroupAddress, tt: TranslatorType, dpt: DPType[DataPointValueType, PrimitveType], name: String = "", net: Network = Network.default){
    this(dpt)
    master = new StateDevice(destAddress, tt, dpt, name, net)
    master writeSubscribe { (value: PrimitveType) =>
      val pvalue = proxyFun(value)
      this map {_ write pvalue}
    }
  }

  def send(value: DataPointValueType) = {
    val pvalue = proxyFun(value.value)
    this map {_ write pvalue}
  }

  //This Function is called right before forwarding to all
  //registered Devices starts
  def addProxyFunction(fun: (PrimitveType) => PrimitveType) = proxyFun = fun
}