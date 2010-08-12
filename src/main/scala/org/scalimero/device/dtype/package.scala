package org.scalimero.device.dtype {
  trait implicits extends Num2ByteFloat.implicits with DateTime.implicits
    with Num2ByteUnsigned.implicits with Num4ByteUnsigned.implicits
    with Num8BitUnsigned.implicits with String.implicits
}

package org.scalimero.device {
  package object dtype extends dtype.implicits
}
