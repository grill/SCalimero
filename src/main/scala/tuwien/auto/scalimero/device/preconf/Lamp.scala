
package tuwien.auto.scalimero.device.preconf

import tuwien.auto.scalimero.device._
import tuwien.auto.scalimero.device.dtype._
import tuwien.auto.scalimero.device.dtype.Boolean._
import tuwien.auto.scalimero.device.dtype.translatortype._

import tuwien.auto.calimero.GroupAddress

object Lamp extends TypeOfDevice {
  def apply(address : GroupAddress) = new Lamp(address)
  type PrimitiveType = Boolean
  type DataPointValueType = BooleanType
}

class Lamp(address: GroupAddress) extends StateDevice(address, SWITCH){
  def turn(value: BooleanValue) = send(value)

  override val events : Map[Any, Boolean => Boolean] = Map (
    on ->   {(b: Boolean) => b},
    off ->  {(b: Boolean) => !b}
  )
}
