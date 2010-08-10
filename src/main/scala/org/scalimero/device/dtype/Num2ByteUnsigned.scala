package org.scalimero.device.dtype

import org.scalimero.device.dtype._

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

abstract class Num2ByteUnsignedValue(val value: Int) extends DPValue {
  val unit : String = ""
  val min = 0
  val max = 65536
  
  if(value < min || value > max)
		throw new OutOfBoundsException(value.toString, min.toString + " " + unit, max.toString + " " + unit)
  
  override def toString = unit match {
    case "" => value.toString
    case s : String => value.toString + " " + s
  }
}

object Num2ByteUnsigned {
  class BRIGHTNESS(override val value : Int) extends Num2ByteUnsignedValue(value) {
    override val unit = "lx"
  }

  class ELECTRICAL_CURRENT(override val value : Int) extends Num2ByteUnsignedValue(value) {
    override val unit = "mA"
  }

  class PROP_DATATYPE(override val value : Int) extends Num2ByteUnsignedValue(value) {
    override val unit = ""
  }

  class TIMEPERIOD(override val value : Int) extends Num2ByteUnsignedValue(value) {
    override val unit = "ms"
  }

  class TIMEPERIOD_10(override val value : Int) extends Num2ByteUnsignedValue(value) {
    override val max = 655350
    override val unit = "ms"
  }

  class TIMEPERIOD_100(override val value : Int)  extends Num2ByteUnsignedValue(value) {
    override val max = 6553500
    override val unit = "ms"
  }

  class TIMEPERIOD_HOURS(override val value : Int) extends Num2ByteUnsignedValue(value) {
    override val unit = "h"
  }

  class TIMEPERIOD_MIN(override val value : Int) extends Num2ByteUnsignedValue(value) {
    override val unit = "min"
  }

  class TIMEPERIOD_SEC(override val value : Int) extends Num2ByteUnsignedValue(value) {
    override val unit = "s"
  }

  class VALUE_2_UCOUNT(override val value : Int) extends Num2ByteUnsignedValue(value) {
    override val unit = "pulses"
  }
}

