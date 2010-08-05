package org.scalimero.device.preconf

import org.scalimero.device._
import org.scalimero.device.dtype._

class Lamp(address: String) extends StateDevice( address, BOOLEAN, Switch){
	def turn(value: Boolean) = send(value)
	
	override val events : Map[Any, Boolean => Boolean] = Map (
		on ->		{(b: Boolean) => b},
		off ->	{(b: Boolean) => !b}
	)
}