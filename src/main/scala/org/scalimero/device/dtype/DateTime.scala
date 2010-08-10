package org.scalimero.device.dtype

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

import java.text.SimpleDateFormat

//Not tested
abstract class DateTimeType(dpt: DPT) extends DPType[SimpleDateFormat](dpt) {
  val dptx = new DPTXlatorDateTime (dpt.getID)

  def translate(value: String): SimpleDateFormat = {
    dptx.setValue(value)
    new SimpleDateFormat(dpt.getUnit).parse(dptx.getValue)
  }

  def translate(value: SimpleDateFormat): String = {
    dptx.setValue(value.toString)
    dptx.getValue
  }

  def translate (value: Array[Byte]): String = {
    dptx.setData(value)
    dptx.getValue
  }
}

object DATETIME extends DateType(DPTXlatorDateTime.DPT_DATE_TIME)