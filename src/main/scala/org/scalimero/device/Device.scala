package org.scalimero.device

import org.scalimero.device.dtype._

import tuwien.auto.calimero._  
import tuwien.auto.calimero.datapoint._
import tuwien.auto.calimero.dptxlator._

abstract class Device[T](destAddress:String, tt: TranslatorType, dpt: DPType[T], name: String, net: Network){
    val dp: Datapoint
	
	def send(value: T) = net.send(dpt.translate(value))
}

case class CommandDevice(destAddress:String, tt: TranslatorType, dpt: DPType[T], name: String = "", net: Network = Network.default) 
	extends Device(destAddress, tt, dpt, name, net){
	override val dp = new CommandDP(new GroupAddress(destAddress), name, tt.mainNumber, dpt.id)
}

case class StateDevice[T](destAddress:String, tt: TranslatorType, dpt: DPType[T], name: String = "", net: Network = Network.default) 
	extends Device(destAddress, tt, dpt, name, net){
	override val dp = new StateDP(new GroupAddress(destAddress), name, tt.mainNumber, dpt.id)
	
	def read(): T = dpt.translate(net.read)
}