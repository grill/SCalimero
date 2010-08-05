package org.scalimero.dsl

import tuwien.auto.calimero.link._
import tuwien.auto.calimero.link.medium._
import tuwien.auto.calimero.process._
import tuwien.auto.calimero._

object Network {
  var default : Network = null
  var defaultMedium = TPSettings.TP1
  
  def apply(router : String) = {
    default = new Network(router)
    default
  }
}

class Network(var router : String) {
  var medium = Network.defaultMedium
  var nl : KNXNetworkLink = null
  var opened = false
  var pc : ProcessCommunicator = null
  
  object pl extends ProcessListener{
    def detached(e : DetachEvent) {}
    def groupWrite(e : ProcessEvent) {}
  }
  
  def open {
    opened = true
    pc = new ProcessCommunicatorImpl(networkLink)
    pc.addProcessListener(pl)
  }
  
  def close {
    opened = false
    if(nl != null && nl.isOpen)
      nl.close
  }
  
  def send(dp : Datapoint, value : String) = pc.write(dp, value)
  def read(dp : Datapoint) = pc.read(dp)
  
  def networkLink = if(nl != null && nl.isOpen) nl else {
    new KNXNetworkLinkIP(router, medium)
  }
}
