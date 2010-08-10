package org.scalimero.device.dtype

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

import java.text.SimpleDateFormat

//Not tested
abstract class StringType(dpt: DPT) extends DPType[SimpleDateFormat](dpt) {
  val dptx = new DPTXlatorString (dpt.getID)

  def translate(value: String): String = value
  def translate (value: Array[Byte]): String = {
    dptx.setData(value)
    dptx.getValue
  }
}

object STRING_8859_1 extends DateType(DPTXlatorDateTime.DPT_STRING_8859_1)
object STRING_ASCII extends DateType(DPTXlatorDateTime.DPT_STRING_ASCII)