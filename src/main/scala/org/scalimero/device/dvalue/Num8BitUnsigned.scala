package org.scalimero.device.dvalue

import org.scalimero.device.dtype._

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

abstract class Num8BitUnsignedValue(val value: Int) extends DPValue {
  val unit : String = ""
  val min = 0
  val max = 255
  
  if(value < min || value > max)
		throw new OutOfBoundsException(value.toString, min.toString + " " + unit, max.toString + " " + unit)
  
  override def toString = unit match {
    case "" => value.toString
    case s : String => value.toString + " " + s
  }
}

object Num8BitUnsigned {
  class ANGLE(override val value : Int) extends Num8BitUnsignedValue(value) {
    override val max = 360
    override val unit = "°"
  }
  
  class DECIMALFACTOR(override val value : Int) extends Num8BitUnsignedValue(value) {
    override val unit = "ratio"
  }
  
  class PERCENT_U8(override val value : Int) extends Num8BitUnsignedValue(value) {
    override val unit = "%"
  }
  
  class SCALING(override val value : Int) extends Num8BitUnsignedValue(value) {
    override val max = 100
    override val unit = "%"
  }
  
  class VALUE_1_UCOUNT(override val value : Int) extends Num8BitUnsignedValue(value) {
    override val unit = "pulses"
  }
}

