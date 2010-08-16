package org.scalimero.device.preconf

import org.scalimero.device._
import org.scalimero.device.dtype._
import org.scalimero.device.dtype.Num2ByteFloat._
import org.scalimero.device.dtype.translatortype._

import tuwien.auto.calimero.GroupAddress

object Temperature extends TypeOfDevice {
  def apply(address : GroupAddress) = new Temperature(address)
  type PrimitiveType = Float
  type DataPointValueType = Num2ByteFloatType[TEMPERATURE]
}

class Temperature(address: GroupAddress) extends StateDevice(address, NUM2OCTET_FLOAT, TEMPERATURE){
  def set(value: TEMPERATURE) = send(value)

  override val events : Map[Any, (Float) => Boolean] = Map ()
}
