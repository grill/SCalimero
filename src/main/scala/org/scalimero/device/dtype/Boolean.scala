import org.scalimero.device.dtype._

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

package object dtype {

abstract trait BooleanValue
abstract class BooleanType(dpt: DPT) extends DPType[Boolean](dpt) {
    val dptx = new DPTXlatorBoolean (DPTXlatorBoolean.DPT_SWITCH.getID)
	
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

trait False extends BooleanValue
object False
trait True extends BooleanValue
object True
implicit def true2bool(t: True.type) = true
implicit def false2bool(t: False.type) = false

class ACK extends DPValue
object ACK extends BooleanType(DPTXlatorBoolean.DPT_ACK )
object no_action extends ACK with False
object acknwoledge extends ACK with True

class Alarm extends DPValue
object Alarm extends BooleanType(DPTXlatorBoolean.DPT_ALARM )
object no_alarm extends Alarm with False
object alarm extends Alarm with True

class Switch extends DPValue
object Switch extends BooleanType(DPTXlatorBoolean.DPT_SWITCH )
object off extends Switch with False
object on extends Switch with True

}