package org.scalimero.device.dtype

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

abstract class BooleanType(dpt: DPT) extends DPType[BooleanType, Boolean](dpt) {
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

package object Boolean{
  object ACK extends BooleanType(DPTXlatorBoolean.DPT_ACK )
  object ALARM extends BooleanType(DPTXlatorBoolean.DPT_ALARM )
  object SWITCH extends BooleanType(DPTXlatorBoolean.DPT_SWITCH )
  object BINARYVALUE extends BooleanType(DPTXlatorBoolean.DPT_BINARYVALUE )
  object BOOL extends BooleanType(DPTXlatorBoolean.DPT_BOOL )
}

abstract class BooleanValue extends DPValue

class False extends BooleanValue{
  override val value = false
}
object False
class True extends BooleanValue {
  override val value = true
}
object True

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

//BOOL
/**
 *  primitive types are used in this case
 **/

/** We need help, because we don't know which state has which value 
 *
//DIMSENDSTYLE
object start/stop extends False
object cyclic extends True

//ENABLE
object disable extends False
object enable extends True

//INPUTSOURCE
object fixed extends False
object calculated extends True
          DPT ID 1.014, Input source; values fixed, calculated.
//INVERT
object not_inverted extends False
object inverted extends True
          DPT ID 1.012, Invert; values not inverted, inverted.
//LOGICAL_FUNCTION
object off extends False
object on extends True
          DPT ID 1.021, Logical function; values OR, AND.
//OCCUPANCY
object off extends False
object on extends True
          DPT ID 1.018, Occupancy; values not occupied, occupied.
//OPENCLOSE
object off extends False
object on extends True
          DPT ID 1.009, Open/Close; values open, close.
//RAMP
object off extends False
object on extends True
          DPT ID 1.004, Ramp; values no ramp, ramp.
//RESET
object off extends False
object on extends True
          DPT ID 1.015, Reset; values no action (dummy), reset (trigger).
//SCENE_AB
object off extends False
object on extends True
          DPT ID 1.022, Scene A/B; values scene A, scene B.
//SHUTTER_BLINDS_MODE
object off extends False
object on extends True
          DPT ID 1.023, Shutter/Blinds mode; values only move up/down mode (shutter), move up/down + step-stop mode (blind).
//START
object off extends False
object on extends True
          DPT ID 1.010, Start; values stop, start.
//STATE
object off extends False
object on extends True
          DPT ID 1.011, State; values inactive, active.
//STEP
object off extends False
object on extends True
          DPT ID 1.007, Step; values decrease, increase.
//SWITCH
object off extends False
object on extends True
          DPT ID 1.001, Switch; values off, on.
//TRIGGER
object off extends False
object on extends True
          DPT ID 1.017, Trigger; values trigger, trigger.
//UPDOWN
object off extends False
object on extends True
          DPT ID 1.008, Up/Down; values up, down.
//WINDOW_DOOR
object off extends False
object on extends True
          DPT ID 1.019, Window/Door; values closed, open.
*/