package org.scalimero.device.dvalue

import org.scalimero.device.dtype._

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

abstract class Num2ByteFloatValue(val value: Float) extends DPValue {
  val unit : String = ""
  val min = -670760
  val max = 670760
  
  if(value < min || value > max)
		throw new OutOfBoundsException(value.toString, min.toString + " " + unit, max.toString + " " + unit)
  
  override def toString = value.toString + " " + unit
}

class AIR_PRESSURE(override val value: Float) extends Num2ByteFloatValue(value) {
  override val min = 0
	override val unit = "Pa"
}

class AIRQUALITY(override val value: Float) extends Num2ByteFloatValue(value) {
  override val min = 0
	override val unit = "ppm"
}

class ELECTRICAL_CURRENT(override val value: Float) extends Num2ByteFloatValue(value) {
	override val unit = "mA"
}

class HUMIDITY(override val value: Float) extends Num2ByteFloatValue(value) {
  override val min = 0
	override val unit = "%"
}

class INTENSITY_OF_LIGHT(override val value: Float) extends Num2ByteFloatValue(value) {
  override val min = 0
	override val unit = "lx"
}

class KELVIN_PER_PERCENT(override val value: Float) extends Num2ByteFloatValue(value) {
	override val unit = "K/%"
}

class POWER(override val value: Float) extends Num2ByteFloatValue(value) {
	override val unit = "kW"
}

class POWERDENSITY(override val value: Float) extends Num2ByteFloatValue(value) {
	override val unit = "W/m²"
}

class TEMPERATURE(override val value: Float) extends Num2ByteFloatValue(value) {
  override val min = -273
	override val unit = "°C"
}

class TEMPERATURE_DIFFERENCE(override val value: Float) extends Num2ByteFloatValue(value) {
	override val unit = "K"
}

class TEMPERATURE_GRADIENT(override val value: Float) extends Num2ByteFloatValue(value) {
	override val unit = "K/h"
}

class TIME_DIFFERENCE1(override val value: Float) extends Num2ByteFloatValue(value) {
	override val unit = "s"
}

class TIME_DIFFERENCE2su
(override val value: Float) extends Num2ByteFloatValue(value) {
	override val unit = "ms"
}

class VOLTAGE(override val value: Float) extends Num2ByteFloatValue(value) {
	override val unit = "mV"
}

class WIND_SPEED(override val value: Float) extends Num2ByteFloatValue(value) {
  override val min = 0
	override val unit = "m/s"
}

