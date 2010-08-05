package org.scalimero.device.dtype

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

abstract class BooleanValue extends DPValue
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

class False extends BooleanValue
object False
class True extends BooleanValue
object True

//trait ACK extends DPValue
object ACK extends BooleanType(DPTXlatorBoolean.DPT_ACK )
object no_action extends False // with ACK
object acknwoledge extends True // with ACK

//trait Alarm extends DPValue
object Alarm extends BooleanType(DPTXlatorBoolean.DPT_ALARM )
object no_alarm extends False // with Alarm
object alarm extends True // with Alarm

//trait Switch extends DPValue
object Switch extends BooleanType(DPTXlatorBoolean.DPT_SWITCH )
object off extends False // with Switch
object on extends True // with Switch
