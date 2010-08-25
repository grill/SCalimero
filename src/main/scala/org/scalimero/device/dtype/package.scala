package org.scalimero.device.dtype {
  trait implicits extends Num2ByteFloat.implicits with DateTime.implicits
    with Num2ByteUnsigned.implicits with Num4ByteUnsigned.implicits
    with Num8BitUnsigned.implicits with String.implicits with Boolean.implicits {
    implicit def dptype2native[T](dpv : DPValue[T]) = dpv.value
  }
}

package org.scalimero.device {
  package object dtype extends dtype.implicits
}
