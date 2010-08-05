package org.scalimero.util

import scala.concurrent.ops._

trait EventHelper[T] {
  class EventCallback(var fun : () => Unit) {
    def apply() = fun()
    def update(newFun : => Unit) = fun = newFun _
    def detach {unsubscribe(this)}
  }

  val events : List[(T => Boolean, Any)]
  var callbacks = Map[Any, List[EventCallback]]() withDefaultValue Nil
  
  def eventList(in : T) =
    events filter {_._1(in)} map {_._2}
  
  def subscribe(event : Any)(callback : => Unit) = {
    if (events forall {_._2 != event})
      throw new NoSuchEventException("No event " + event)
    
    val ecallback = new EventCallback(callback _)
    callbacks = callbacks updated (event, ecallback :: callbacks(event))
    ecallback
  }
  
  def unsubscribe(callback : EventCallback) = {
    callbacks = callbacks map ((e) => (e._1, e._2 filterNot (_==callback)))
  }
  
  def callEvents(value : T){
    events.map((eventSig) => if(eventSig._1(value)) callbacks(eventSig._2).map(op => spawn(op())))
  }
}

class NoSuchEventException(msg : String) extends Exception(msg)
