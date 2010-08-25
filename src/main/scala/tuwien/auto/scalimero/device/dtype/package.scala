package tuwien.auto.scalimero.device.dtype {
  trait implicits extends Num2ByteFloat.implicits with DateTime.implicits
    with Num2ByteUnsigned.implicits with Num4ByteUnsigned.implicits
    with Num8BitUnsigned.implicits with String.implicits with Boolean.implicits
}

package tuwien.auto.scalimero.device {
  package object dtype extends dtype.implicits
}
