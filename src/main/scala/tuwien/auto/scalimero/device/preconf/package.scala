package tuwien.auto.scalimero.device

import tuwien.auto.scalimero.device.dtype._
import tuwien.auto.calimero._

package object preconf {
  implicit def true2bool(t: True.type) = true
  implicit def false2bool(t: False.type) = false
  implicit def true2bool(t: True) = true
  implicit def false2bool(t: False) = false
  
  implicit def bool2True_False(t: Boolean) = if(t) True else False
  
  implicit def str2groupaddr(s: String) = new GroupAddress(s) 
}
