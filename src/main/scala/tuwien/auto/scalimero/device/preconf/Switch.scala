package tuwien.auto.scalimero.device.preconf

import tuwien.auto.scalimero.device._
import tuwien.auto.scalimero.device.dtype._
import tuwien.auto.scalimero.device.dtype.Boolean._
import tuwien.auto.scalimero.device.dtype.translatortype._

import tuwien.auto.calimero.GroupAddress

object Switch extends TypeOfDevice {
  def apply(address : GroupAddress) = new Switch(address)
  type PrimitiveType = Boolean
  type DataPointValueType = BooleanType
}

class Switch(address: GroupAddress) extends StateDevice(address, TRIGGER){
  def turn(value: BooleanValue) = send(value)

  override val events : Map[Any, Boolean => Boolean] = Map ()
}