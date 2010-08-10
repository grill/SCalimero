package org.scalimero.device.dtype

import org.scalimero.device.dtype._

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

abstract class Num4ByteUnsignedValue(val value: Long) extends DPValue {
  val unit : String = ""
  val min = 0L
  val max = 4294967295L
  
  if(value < min || value > max)
		throw new OutOfBoundsException(value.toString, min.toString + " " + unit, max.toString + " " + unit)
  
  override def toString = unit match {
    case "" => value.toString
    case s : String => value.toString + " " + s
  }
}

object Num4ByteUnsigned {
  class VALUE_4_UCOUNT(override val value : Long) extends Num4ByteUnsignedValue(value) {
    override val unit = "pulses"
  }
}

