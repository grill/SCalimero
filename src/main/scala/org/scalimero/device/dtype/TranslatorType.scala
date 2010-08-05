package org.scalimero.device.dtype

abstract class TranslatorType{
	val mainNumber: Int
}

object TranslatorType{
	implicit def Int2TT(mainNumber: Int) = mainNumber match {
		case BOOLEAN.mainNumber => BOOLEAN
		case _ => throw new TranslatorTypeNotFoundException(mainNumber.toString); NOTHING
	}
}

object NOTHING extends TranslatorType{
	override val mainNumber = -1
}

object BOOLEAN extends TranslatorType{
	override val mainNumber = 1
}
