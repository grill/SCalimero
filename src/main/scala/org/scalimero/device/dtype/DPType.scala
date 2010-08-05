package org.scalimero.device.dtype

import org.scalimero.device.dtype._

import tuwien.auto.calimero.dptxlator._

abstract class DPType[T](val dpt: DPT) {
	def id = dpt.getID()
	def translate(value: String): T
	def translate(value: T): String
	def translate(value: Array[Byte]): String
}
/*
object DPType{
	implicit def dpt2dptype(dpt: DPT) = new DPType(DPT)
}*/

class DPValue