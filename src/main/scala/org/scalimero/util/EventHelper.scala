package scalimero.util

import scala.collection.immutable.Map

abstract trait EventHelper[T] {
  val events : List[(T => Boolean, Any)]
  var callbacks = Map[Any, List[() => Unit]]() withDefaultValue Nil
  
  def eventList(in : T) =
    events filter {_._1(in)} map {_._2}
  
  def subscribe(event : Any)(callback : => Unit) {
    if (events forall {_._2 != event})
      throw new NoSuchEventException("No event " + event)
    
    callbacks = callbacks updated (event, callback _ :: callbacks(event))
  }
  
  def callEvents(value : T){
    events.map((eventSig) => if(eventSig._1(value)) callbacks(eventSig._2).map(_()))
  }
}

class NoSuchEventException(msg : String) extends Exception(msg)
