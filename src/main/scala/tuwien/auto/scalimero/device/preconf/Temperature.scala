package tuwien.auto.scalimero.device.preconf

import tuwien.auto.scalimero.device._
import tuwien.auto.scalimero.device.dtype._
import tuwien.auto.scalimero.device.dtype.Num2ByteFloat._
import tuwien.auto.scalimero.device.dtype.translatortype._

import tuwien.auto.calimero.GroupAddress

object Temperature extends TypeOfDevice {
  def apply(address : GroupAddress) = new Temperature(address)
  type PrimitiveType = Float
  type DataPointValueType = Num2ByteFloatType[TEMPERATURE]
}

class Temperature(address: GroupAddress) extends StateDevice(address, TEMPERATURE){
  def set(value: TEMPERATURE) = send(value)

  override val events : Map[Any, (Float) => Boolean] = Map ()
}
