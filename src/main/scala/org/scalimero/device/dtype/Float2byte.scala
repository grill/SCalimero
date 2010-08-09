package org.scalimero.device.dtype

import org.scalimero.device.dvalue._

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

object AIR_PRESSURE extends Float2ByteType(DPTXlator2ByteFloat.DPT_AIR_PRESSURE ){
	implicit def int2airp(i: Int) = new AIR_PRESSURE(i.toFloat)
	implicit def float2airp(i: Float) = new AIR_PRESSURE(i)
}
