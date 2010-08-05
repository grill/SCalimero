package org.scalimero.devices.test

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers
import scala.collection.mutable.Stack

class DeviceSpec extends Spec with ShouldMatchers {
  
  describe("A Command Datapoint") {
    it("should be able to create instances with differnt mainnumbers and dptid's"){
      val cdptbool = StateDevice("1/1/1", BOOLEAN, Switch) 
    }
    
    it("should be able to send knxmessages with differnt types"){
     /* cdptbool send true
      cdptbool send false
      cdptint send 0
      cdptint send 100
      cdptfloat send 0.0
      cdptfloat send 100.0*/
    }

    /*
     * to help users, who want to learn or use the KNX protocol
     * on a very low layer
     */
    it("should be able to send knxmessages on byte level") {
      //cdptbool send (01000101 byte)
    }
  }
}