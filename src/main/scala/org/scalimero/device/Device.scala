package org.scalimero.device

import scala.actors.Actor
import scala.actors.Actor._

import org.scalimero.device.dtype._
import org.scalimero.dsl._

import tuwien.auto.calimero._  
import tuwien.auto.calimero.datapoint._
import tuwien.auto.calimero.dptxlator._

abstract class Device[T](destAddress:String, tt: TranslatorType, dpt: DPType[T], name: String, net: Network) extends Actor{
    val dp: Datapoint
	
	override def act() {
		loop{
			react {
				case _ => println("A message arrived Sir!")
			}
		}
	}
	net.subscribe(this, List(new GroupAddress(destAddress)))
	def send(value: T) = net.send(dp, dpt.translate(value))
	def detach() = net.unsubscribe(this)
}

case class CommandDevice[T](destAddress:String, tt: TranslatorType, dpt: DPType[T], name: String = "", net: Network = Network.default) 
	extends Device(destAddress, tt, dpt, name, net){
	override val dp = new CommandDP(new GroupAddress(destAddress), name, tt.mainNumber, dpt.id)
}

case class StateDevice[T](destAddress:String, tt: TranslatorType, dpt: DPType[T], name: String = "", net: Network = Network.default) 
	extends Device(destAddress, tt, dpt, name, net){
	override val dp = new StateDP(new GroupAddress(destAddress), name, tt.mainNumber, dpt.id)
	
	def read(): T = dpt.translate(net.read(dp))
}