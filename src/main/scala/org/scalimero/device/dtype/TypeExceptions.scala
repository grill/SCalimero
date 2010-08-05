package org.scalimero.device.dtype

class OutOfBoundsException(value: String, min: String, max: String) extends Exception("The value = " + value +
	"is out of bounds! Please choose a values from " + min + " to " + max + ".")
	
class TranslatorTypeNotFoundException(msg: String) extends Exception("The value " + msg + "is not a vaild mainNumber!")