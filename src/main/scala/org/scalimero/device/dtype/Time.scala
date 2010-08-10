package org.scalimero.device.dtype

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

import java.text.SimpleDateFormat

//Not tested
abstract class TimeType(dpt: DPT) extends DPType[SimpleDateFormat](dpt) {
  val dptx = new DPTXlatorTime (dpt)

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

object  extends DateType(DPTXlatorDateTime.DPT_TIMEOFDAY)