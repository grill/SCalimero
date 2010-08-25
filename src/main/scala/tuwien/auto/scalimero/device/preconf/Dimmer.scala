
package tuwien.auto.scalimero.device.preconf

import tuwien.auto.scalimero.device._
import tuwien.auto.scalimero.device.dtype._
import tuwien.auto.scalimero.device.dtype.Num8BitUnsigned._
import tuwien.auto.scalimero.device.dtype.translatortype._

import tuwien.auto.calimero.GroupAddress

object Dimmer extends TypeOfDevice  {
  def apply(address : GroupAddress) = new Dimmer(address)
  type PrimitiveType = Int
  type DataPointValueType = Num8BitUnsignedType[SCALING]
}

class Dimmer(address: GroupAddress) extends StateDevice(address, SCALING){
  def set(value: SCALING) = send(value)

  override val events : Map[Any, Int => Boolean] = Map ()
}
