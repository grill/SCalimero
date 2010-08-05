package org.scalimero.device

import scala.actors.Actor
import scala.actors.Actor._

import org.scalimero.device.dtype._
import org.scalimero.dsl._
import org.scalimero.util._

import tuwien.auto.calimero._  
import tuwien.auto.calimero.datapoint._
import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.process._

abstract class Device[T](destAddress:String, tt: TranslatorType, dpt: DPType[T], name: String, net: Network) extends Actor with EventHelper[T]{
    val dp: Datapoint
	
	override def act() {
		loop{
			react {
				case p: ProcessEvent => callEvents(dpt.translate(dpt.translate(p.getASDU)))
				case Subscribe(event, callback) => reply { super.subscribe(event)(callback) }
				case UnSubscribe(callback) => super.unsubscribe(callback)
				case _ => println("A message has arrived Sir!")
			}
		}
	}
	net.subscribe(this, List(new GroupAddress(destAddress)))
	def send(value: T) = net.send(dp, dpt.translate(value))
	def detach() = net.unsubscribe(this)
	
	override def subscribe(event: Any) (callback: => Unit) = {
		this !? Subscribe(event, callback _) match {
			case e: EventCallback => e
			case _ => throw new Exception("This happens all the time(hahaha)!")
		}
	}
	override def unsubscribe(callback: EventCallback) {
		this ! UnSubscribe(callback)
	}
	
	case class Subscribe(event: Any, callback: () => Unit)
	case class UnSubscribe(callback: EventCallback)
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