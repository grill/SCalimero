package org.scalimero.device.dvalue

import tuwien.auto.calimero.dptxlator._
import tuwien.auto.calimero.dptxlator.DPTXlator._

abstract class BooleanValue extends DPValue

class False extends BooleanValue
object False
class True extends BooleanValue
object True

object no_action extends False
object acknwoledge extends True

object no_alarm extends False
object alarm extends True

object off extends False
object on extends True
