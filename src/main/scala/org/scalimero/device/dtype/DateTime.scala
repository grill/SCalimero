package org.scalimero.device.dtype

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

import java.text.SimpleDateFormat
import java.util.Date

//Not tested
abstract class DateTimeType(override val dpt: DPT) extends DPType[DateTime.DATETIME, Date](dpt) {
  val dptx = new DPTXlatorDateTime (dpt)

  def translate(value: String): Date = {
    dptx.setValue(value)
    new SimpleDateFormat(dpt.getUnit).parse(dptx.getValue)
  }

  def translate(value: Date): String = {
    dptx.setValue(value.toString)
    dptx.getValue
  }

  def translate (value: Array[Byte]): String = {
    dptx.setData(value)
    dptx.getValue
  }
}

abstract class DateType(override val dpt: DPT) extends DPType[DateTime.DATE, Date](dpt) {
  val dptx = new DPTXlatorDate (dpt)

  def translate(value: String): Date = {
    dptx.setValue(value)
    new SimpleDateFormat(dpt.getUnit).parse(dptx.getValue)
  }

  def translate(value: Date): String = {
    dptx.setValue(value.toString)
    dptx.getValue
  }

  def translate (value: Array[Byte]): String = {
    dptx.setData(value)
    dptx.getValue
  }
}

abstract class TimeType(override val dpt: DPT) extends DPType[DateTime.TIME, Date](dpt) {
  val dptx = new DPTXlatorTime (dpt)

  def translate(value: String): Date = {
    dptx.setValue(value)
    new SimpleDateFormat(dpt.getUnit).parse(dptx.getValue)
  }

  def translate(value: Date): String = {
    dptx.setValue(value.toString)
    dptx.getValue
  }

  def translate (value: Array[Byte]): String = {
    dptx.setData(value)
    dptx.getValue
  }
}

package object DateTime{
  object DATETIME extends DateTimeType(DPTXlatorDateTime.DPT_DATE_TIME)
  class DATETIME(val value: SimpleDateFormat) extends DPValue
  
  object DATE extends DateType(DPTXlatorDate.DPT_DATE )
  class DATE(val value: SimpleDateFormat) extends DPValue

  object TIME extends TimeType(DPTXlatorTime.DPT_TIMEOFDAY)
  class TIME(val value: SimpleDateFormat) extends DPValue
}