package org.scalimero.connection

import tuwien.auto.calimero.link._
import tuwien.auto.calimero.link.medium._
import tuwien.auto.calimero.process._
import tuwien.auto.calimero._
import tuwien.auto.calimero.datapoint._
import scala.actors.Actor
import scala.actors.Actor._

object Network {
  var default : Network = null
  var defaultMedium = TPSettings.TP1
  
  def apply(router : String, medium : KNXMediumSettings = defaultMedium) = {
    default = new Network(router, medium)
    default
  }
  
  def open = default.open
  def close = default.close
}

case class WriteEvent(value : String, destination : GroupAddress)
class NoNetworkException extends Exception("No Network!")

class Network(var router : String, var medium : KNXMediumSettings = Network.defaultMedium) {
  var nl : KNXNetworkLink = null
  var opened = false
  var pc : ProcessCommunicator = null
  
  var gasubscriptions = Map[GroupAddress,List[{def !(msg : Any) : Unit}]]() withDefaultValue Nil
  var globalsubscriptions = List[{def !(msg : Any) : Unit}]()
  
  def subscriptions = gasubscriptions.flatMap{_._2} ++ globalsubscriptions
  def subscriptions(ga : GroupAddress) = gasubscriptions(ga) ::: globalsubscriptions
  
  val act : {def !(msg : Any) : Unit} = actor {
    loop {
      react(
        actorBody
      )
    }
  }
  
  def actorBody : PartialFunction[Any, Unit] = {
    case Subscribe(a, gas) => gas foreach {ga => gasubscriptions = gasubscriptions updated (ga, a :: gasubscriptions(ga))}
    case GSubscribe(a) => globalsubscriptions = a :: globalsubscriptions
    case Unsubscribe(a) => {
      gasubscriptions = gasubscriptions filterNot {_._2 == a}
      globalsubscriptions = globalsubscriptions filterNot {_ == a}
    }
    case e : DetachEvent => subscriptions foreach {_ ! e}
    case e : ProcessEvent => subscriptions(e.getDestination) foreach {_ ! e}
    case e : WriteEvent => subscriptions(e.destination) foreach {_ ! e}
  }
  
  case class Subscribe(a : {def !(msg : Any) : Unit}, ga : List[GroupAddress])
  case class GSubscribe(a : {def !(msg : Any) : Unit})
  case class Unsubscribe(a : {def !(msg : Any) : Unit})
  object pl extends ProcessListener{
    def detached(e : DetachEvent) {act ! e}
    def groupWrite(e : ProcessEvent) {act ! e}
  }
  
  def subscribe(a : {def !(msg : Any) : Unit}, ga : List[GroupAddress]) = act ! Subscribe(a : {def !(msg : Any) : Unit}, ga : List[GroupAddress])
  def subscribe(a : {def !(msg : Any) : Unit}) = act ! GSubscribe(a)
  def unsubscribe(a : {def !(msg : Any) : Unit}) = act ! Unsubscribe(a : {def !(msg : Any) : Unit})
  
  def open {
    if(! opened) {
      pc = new ProcessCommunicatorImpl(networkLink)
      pc.addProcessListener(pl)
      opened = true
    }
  }
  
  def close {
    if(opened) {
      opened = false
      if(nl != null && nl.isOpen)
        nl.close
    }
  }
  
  def send(dp : Datapoint, value : String) {
    if(opened) {
      act ! WriteEvent(value, dp.getMainAddress)
      pc.write(dp, value)
    } else {
      throw new NoNetworkException; null
    }
  }
  def readRequest(dst : GroupAddress) {
    if(opened) {
      networkLink.sendRequest(dst, Priority.LOW, DataUnitBuilder.createCompactAPDU(0x00, null))
    } else {
      throw new NoNetworkException; null
    }
  }
  def read(dp : Datapoint) = if(opened){pc.read(dp)} else {
      throw new NoNetworkException; null
    }
  
  def networkLink = if(nl != null && nl.isOpen) nl else {
    new KNXNetworkLinkIP(router, medium)
  }
  
  def apply(stuff : =>Unit) {
    val olddefault = Network.default
    Network.default = this
    stuff
    Network.default = olddefault
  }
}

//class Network(var router : String, var medium : KNXMediumSettings = Network.defaultMedium) {
//  var nl : KNXNetworkLink = null
//  var opened = false
//  var pc : ProcessCommunicator = null
//  
//  val act = actor {
//    var subscriptions = Map[GroupAddress,List[Actor]]() withDefaultValue Nil
//    loop {
//      react{
//        case Subscribe(a, gas) => gas foreach {ga => subscriptions = subscriptions updated (ga, a :: subscriptions(ga))}
//        case Unsubscribe(a) => subscriptions filterNot {_._2 == a}
//        case e : DetachEvent => subscriptions flatMap {_._2} foreach {_ ! e}
//        case e : ProcessEvent => subscriptions(e.getDestination) foreach {_ ! e}
//        case e : WriteEvent => subscriptions(e.destination) foreach {_ ! e}
//      }
//    }
//  }
//  
//  case class Subscribe(a : Actor, ga : List[GroupAddress])
//  case class Unsubscribe(a : Actor)
//  object pl extends ProcessListener{
//    def detached(e : DetachEvent) {act ! e}
//    def groupWrite(e : ProcessEvent) {act ! e}
//  }
//  
//  def subscribe(a : Actor, ga : List[GroupAddress]) = act ! Subscribe(a : Actor, ga : List[GroupAddress])
//  def unsubscribe(a : Actor) = act ! Unsubscribe(a : Actor)
//  
//  def open {
//    pc = new ProcessCommunicatorImpl(networkLink)
//    pc.addProcessListener(pl)
//    opened = true
//  }
//  
//  def close {
//    opened = false
//    if(nl != null && nl.isOpen)
//      nl.close
//  }
//  
//  def send(dp : Datapoint, value : String) = {
//    act ! WriteEvent(value, dp.getMainAddress)
//    pc.write(dp, value)
//  }
//  def read(dp : Datapoint) = pc.read(dp)
//  
//  def networkLink = if(nl != null && nl.isOpen) nl else {
//    new KNXNetworkLinkIP(router, medium)
//  }
//  
//  def apply(stuff : =>Unit) {
//    val olddefault = Network.default
//    Network.default = this
//    stuff
//    Network.default = olddefault
//  }
//}
