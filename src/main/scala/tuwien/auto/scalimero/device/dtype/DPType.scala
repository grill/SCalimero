package tuwien.auto.scalimero.device.dtype

import tuwien.auto.scalimero.device.dtype._

import tuwien.auto.calimero.dptxlator._

abstract class DPType[DataPointValueType <: DPValue[PrimitiveType], PrimitiveType](val dpt: DPT) {
  def id = dpt.getID()
  def mainNumber = id.split('.')(0).toInt
  def translate(value: String): PrimitiveType
  def translate(value: PrimitiveType): String
  def translate(value: Array[Byte]): String
}
/*
object DPType{
  implicit def dpt2dptype(dpt: DPT) = new DPType(DPT)
}*/

abstract class DPValue[PrimitiveType] {
  val value: PrimitiveType
}

abstract class TypeOfDevice {
  type PrimitiveType
  type DataPointValueType
}

class OutOfBoundsException(value: String, min: String, max: String) extends Exception("The value " + value +
  " is out of bounds! Please choose a value from " + min + " to " + max + ".")
