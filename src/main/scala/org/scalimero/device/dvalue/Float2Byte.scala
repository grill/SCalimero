package org.scalimero.device.dvalue

import org.scalimero.device.dtype._

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

abstract class Float2ByteValue(value: Float) extends DPValue

class AIR_PRESSURE(value: Float) extends Float2ByteValue(value) {
	if(value < 0 || value > 670760)
		throw new OutOfBoundsException(value.toString, "0", "+670760 Pa")
}

