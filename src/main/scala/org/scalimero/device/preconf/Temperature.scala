package org.scalimero.device.preconf

import org.scalimero.device._
import org.scalimero.device.dtype._
import org.scalimero.device.dtype.Num2ByteFloat._
import org.scalimero.device.dtype.translatortype._

import tuwien.auto.calimero.GroupAddress

object Temperature {
  def apply(address : GroupAddress) = new Temperature(address)
}

class Temperature(address: GroupAddress) extends StateDevice(address, NUM2OCTET_FLOAT, TEMPERATURE){
  def set(value: TEMPERATURE) = send(value)

  override val events : Map[Any, (Float) => Boolean] = Map ()
}
