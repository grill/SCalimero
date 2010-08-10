package org.scalimero.device

import org.scalimero.device.dtype._
import org.scalimero.dsl._
import org.scalimero.util._
import org.scalimero.device._

import tuwien.auto.calimero.GroupAddress

class GroupDevice[T](dpt: DPType[T]) extends TCommandDevice[T] with Seq[T]{
  var proxyFun = (value: T) => value
  var reciever: StateDevice[T] = null
  
  def this(destAddress: GroupAddress, tt: TranslatorType, dpt: DPType[T], name: String = "", net: Network = Network.default){
    this(dpt)
    reciever = new StateDevice(destAddress, tt, dpt, name, net)
  }
  
  def add(d: Device[T]) {
    ;
  }
  
  def add(d: Device[T], fun: (T) => T){
    ;
  }
  
  def send(value: T) = {
    ;
  }
  
  //This Function is called right before forwarding to all
  //registered Devices starts
  def addProxyFunction(fun: (T) => T) = proxyFun = fun
}