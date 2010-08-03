package main.scala.util

import scala.collection.immutable.Map

trait EventHelperDSL[T] {
	var events: Map[Any, Any]
	
	def 
	
	def update(newval: T) = {
		eventify(newval).map(
			events(_) match {
				case fun: ((T) => Unit) => fun(newval)
				case fun: ((Exception) => Unit) => fun(newval)
				case fun: ((Any) => Unit) => fun(newval)
				case fun: (() => Unit) => fun()
				case _ => throw new Exception("impossible!!")
			}
		)
	}
	
	def eventify(newval: T): List[Any]
	
	def exceptify(newval: Exception): 
	
	def event(key: Any) (ev: (Any) => Unit) = events + (key -> ev)
	
	def event(key: Any) (ev: () => Unit) = events + (key -> ev)
	
	def remove(key: Any) = events - key
}

trait EventHelper{
	
}