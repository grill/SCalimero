package org.scalimero.device.dvalue

class DPValue

class OutOfBoundsException(value: String, min: String, max: String) extends Exception("The value = " + value +
	"is out of bounds! Please choose a values from " + min + " to " + max + ".")