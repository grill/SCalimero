package org.scalimero.device.dtype

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

abstract class BooleanType(dpt: DPT) extends DPType[BooleanValue, Boolean](dpt) {
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

package object boolean{
  object ACK extends BooleanType(DPTXlatorBoolean.DPT_ACK )
  object ALARM extends BooleanType(DPTXlatorBoolean.DPT_ALARM )
  object SWITCH extends BooleanType(DPTXlatorBoolean.DPT_SWITCH )
  object BINARYVALUE extends BooleanType(DPTXlatorBoolean.DPT_BINARYVALUE )
  object BOOL extends BooleanType(DPTXlatorBoolean.DPT_BOOL )
}

abstract class BooleanValue extends DPValue[Boolean]

class False extends BooleanValue{
  override val value = false
}
object False extends False
class True extends BooleanValue {
  override val value = true
}
object True extends True

//ACK
object no_action extends False
object acknwoledge extends True

//ALARM
object no_alarm extends False
object alarm extends True

//SWITCH
object off extends False
object on extends True

//BINARYVALUE
object low extends False
object high extends True

//ENABLE
object disable extends False
object enable extends True

//RAMP
object no_ramp extends False
object ramp extends True

//STEP
object decrease extends False
object increase extends True

//UPDOWN
object up extends False
object down extends True

//OPENCLOSE
object open extends False
object close extends True

//START
object stop extends False
object start extends True

//STATE
object inactive extends False
object active extends True

//INVERT
object not_inverted extends False
object inverted extends True

//DIMSENDSTYLE
object start/stop extends False
object cyclic extends True

//INPUTSOURCE
object fixed extends False
object calculated extends True

//RESET
//object no_action extends False
object reset extends True

//TRIGGER
//object trigger extends False
object trigger extends True

//OCCUPANCY
object not_occupied extends False
object occupied extends True

//WINDOW_DOOR
object closed extends False
object open extends True

//LOGICAL_FUNCTION
object OR extends False
object AND extends True

//SCENE_AB
object scene_B extends False
object scene_B extends True

//SHUTTER_BLINDS_MODE
object only_move_up/down extends False
object move_up/down_+_step-stop extends True

//SWITCH
object off extends False
object on extends True

//BOOL
/**
 *  primitive types are used in this case
 **/


