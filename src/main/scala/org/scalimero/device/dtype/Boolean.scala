package org.scalimero.device.dtype

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

abstract class BooleanType(dpt: DPT) extends DPType[Boolean](dpt) {
    val dptx = new DPTXlatorBoolean (dpt.getID)
	
	def translate(value: String): Boolean = {
      dptx.setValue(value)
      dptx.getValueBoolean
	}
	
	def translate(value: Boolean): String = {
      dptx.setValue(value) 
      dptx.getValue
	}
	
    def translate (value: Array[Byte]): String = {
		dptx.setData(value)
		dptx.getValue
    }   

}

object ACK extends BooleanType(DPTXlatorBoolean.DPT_ACK )
object ALARM extends BooleanType(DPTXlatorBoolean.DPT_ALARM )
object SWITCH extends BooleanType(DPTXlatorBoolean.DPT_SWITCH )
object BINARYVALUE extends BooleanType(DPTXlatorBoolean.DPT_BINARYVALUE )
object BOOL extends BooleanType(DPTXlatorBoolean.DPT_BOOL )