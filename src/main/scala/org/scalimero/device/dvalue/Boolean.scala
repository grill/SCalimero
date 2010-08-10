package org.scalimero.device.dvalue

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

//ACK
val no_action = false
val acknwoledge = true

//ALARM
val no_alarm = false
val alarm = true

//SWITCH
val off = false
val on = true

//BINARYVALUE
val low = false
val high = true

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
object off extends False
object on extends True
          DPT ID 1.014, Input source; values fixed, calculated.
//INVERT
object off extends False
object on extends True
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