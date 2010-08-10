package org.scalimero.device.preconf

import org.scalimero.device._
import org.scalimero.device.dtype._
import org.scalimero.device.dtype.Boolean._
import org.scalimero.device.dvalue._

object Lamp {
  def apply(address : String) = new Lamp(address)
}

class Lamp(address: String) extends StateDevice(address, BOOLEAN, SWITCH){
	def turn(value: Boolean) = send(value)

	override val events : Map[Any, Boolean => Boolean] = Map (
		on ->	{(b: Boolean) => b},
		off ->	{(b: Boolean) => !b}
	)
}
