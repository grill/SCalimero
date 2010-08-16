package org.scalimero.device.preconf

import org.scalimero.device._
import org.scalimero.device.dtype._
import org.scalimero.device.dtype.Boolean._
import org.scalimero.device.dtype.translatortype._

import tuwien.auto.calimero.GroupAddress

object Switch  with TypeOfDevice {
  def apply(address : GroupAddress) = new Switch(address)
  type PrimitiveType = Boolean
  type DataPointValueType = BooleanType
}

class Switch(address: GroupAddress) extends StateDevice(address, BOOLEAN, TRIGGER){
  def turn(value: BooleanValue) = send(value)

  override val events : Map[Any, Boolean => Boolean] = Map ()
}
