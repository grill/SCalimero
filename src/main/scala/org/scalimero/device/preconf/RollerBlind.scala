package org.scalimero.device.preconf

import org.scalimero.device._
import org.scalimero.device.dtype._
import org.scalimero.device.dtype.Num8BitUnsigned._
import org.scalimero.device.dtype.translatortype._

import tuwien.auto.calimero.GroupAddress

object RollerBlind extends TypeOfDevice {
  def apply(address : GroupAddress) = new RollerBlind(address)
  type PrimitiveType = Int
  type DataPointValueType = Num8BitUnsignedType[SCALING]
}

class RollerBlind(address: GroupAddress) extends StateDevice(address, SCALING){
  def set(value: SCALING) = send(value)

  override val events : Map[Any, Int => Boolean] = Map ()
}
