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

package object num2ByteUnsigned {
  trait implicits {
    implicit def int2BRIGHTNESS(i : Int) = new BRIGHTNESS(i)
    implicit def int2ELECTRICAL_CURRENT(i : Int) = new ELECTRICAL_CURRENT(i)
    implicit def int2PROP_DATATYPE(i : Int) = new PROP_DATATYPE(i)
    implicit def int2TIMEPERIOD(i : Int) = new TIMEPERIOD(i)
    implicit def int2TIMEPERIOD_10(i : Int) = new TIMEPERIOD_10(i)
    implicit def int2TIMEPERIOD_100(i : Int) = new TIMEPERIOD_100(i)
    implicit def int2TIMEPERIOD_HOURS(i : Int) = new TIMEPERIOD_HOURS(i)
    implicit def int2TIMEPERIOD_MIN(i : Int) = new TIMEPERIOD_MIN(i)
    implicit def int2TIMEPERIOD_SEC(i : Int) = new TIMEPERIOD_SEC(i)
    implicit def int2VALUE_2_UCOUNT(i : Int) = new VALUE_2_UCOUNT(i)
  }

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

