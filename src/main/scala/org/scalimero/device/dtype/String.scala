package org.scalimero.device.dtype

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

//Not tested
abstract class StringType(override val dpt: DPT) extends DPType[String](dpt) {
  val dptx = new DPTXlatorString (dpt)

  def translate(value: String): String = value
  def translate (value: Array[Byte]): String = {
    dptx.setData(value)
    dptx.getValue
  }
}

package object string {
  object ISO_8859_1 extends StringType(DPTXlatorString.DPT_STRING_8859_1)
  object ASCII extends StringType(DPTXlatorString.DPT_STRING_ASCII)
}

class STRING(val value: String) extends DPValue