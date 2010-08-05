package org.scalimero.device.dtype

import org.scalimero.device.dtype._

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

abstract class Float2ByteType(dpt: DPT) extends DPType[Float](dpt: DPT) {
    val dptx = new DPTXlator2ByteFloat (dpt.getID)
	
	def translate(value: String): Float = {
      dptx.setValue(value)
      dptx.getValueFloat
	}
	
	def translate(value: Float): String = {
      dptx.setValue(value) 
      dptx.getValue
	}
	
    def translate (value: Array[Byte]): String = {
		dptx.setData(value)
		dptx.getValue
    }   
}
abstract class Float2ByteValue(value: Float) extends DPValue

class AIR_PRESSURE(value: Float) extends Float2ByteValue(value) {
	if(value < 0 || value > 670760)
		throw new OutOfBoundsException(value.toString, "0", "+670760 Pa")
}

object AIR_PRESSURE extends Float2ByteType(DPTXlator2ByteFloat.DPT_AIR_PRESSURE ){
	implicit def int2airp(i: Int) = new AIR_PRESSURE(i.toFloat)
	implicit def float2airp(i: Float) = new AIR_PRESSURE(i)
}
