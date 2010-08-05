package org.scalimero.device.dtype

import org.scalimero.device.dtype._

import tuwien.auto.calimero.dptxlator._

abstract class Float2ByteType(dpt: DPT) extends DPType[Float](dpt: DPT)
abstract class Float2ByteValue(value: Float) extends DPValue

class AIR_PRESSURE(value: Float) extends Float2ByteValue(value) {
	if(value < 0 || value > 670760)
		throw new OutOfBoundsException(value.toString, "0", "+670760 Pa")
}

object AIR_PRESSURE extends Float2ByteType(DPTXlator2ByteFloat.DPT_AIR_PRESSURE ){
	implicit def int2airp(i: Int) = new AIR_PRESSURE(i.toFloat)
	implicit def float2airp(i: Float) = new AIR_PRESSURE(i)
}
