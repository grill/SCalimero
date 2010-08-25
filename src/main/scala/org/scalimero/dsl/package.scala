package org.scalimero

import org.scalimero.device.dtype._
import org.scalimero.device.dtype
import org.scalimero.device.preconf
import tuwien.auto.calimero._

package object dsl extends implicits {
  implicit def true2bool(t: True.type) = true
  implicit def false2bool(t: False.type) = false
  implicit def true2bool(t: True) = true
  implicit def false2bool(t: False) = false
  implicit def bool2True_False(t: Boolean) = if(t) True else False
  
  implicit def str2groupaddr(s: String) = new GroupAddress(s)

/*  val Num2ByteFloat = dtype.Num2ByteFloat
  val Num2ByteUnsigned = dtype.Num2ByteUnsigned
  val Num8BitUnsigned = dtype.Num8BitUnsigned
  val Num4ByteUnsigned = dtype.Num4ByteUnsigned
  val Num3BitControlled = dtype.Num3BitControlled
  val String = dtype.String
  val DateTime = dtype.DateTime
  val Boolean = dtype.Boolean*/
  
  val Network = connection.Network
  
  val Lamp = preconf.Lamp
  val Dimmer = preconf.Dimmer
  val RollerBlind = preconf.RollerBlind
  val Switch = preconf.Switch
  val Temperatrue = preconf.Temperature
}