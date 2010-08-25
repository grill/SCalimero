package org.scalimero.util.test

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers
import org.scalimero.util._

class WriteCallbackHelperSpec extends Spec with ShouldMatchers {
  describe("A WriteCallbackHelper") {
    it ("should allow subscription and correctly call back") {
      val wch = new WriteCallbackHelper[Int]{}
      var check = 0
      wch writeSubscribe {check = _}
      wch callWrites 10
      check should be (10)
      wch callWrites 42
      check should be (42)
    }
    
    it ("should allow unsubscription") {
      val wch = new WriteCallbackHelper[Int]{}
      var check = 0
      val cb = wch writeSubscribe {check = _}
      wch callWrites 10
      check should be (10)
      wch writeUnsubscribe cb
      wch callWrites 42
      check should be (10)
    }
    
    it ("should allow callback updating and detaching") {
      val wch = new WriteCallbackHelper[Int]{}
      var check = 0
      val cb = wch writeSubscribe {check = _}
      wch callWrites 10
      check should be (10)
      cb update {in => check = in*2}
      wch callWrites 21
      check should be (42)
      cb.detach
      wch callWrites 9*6
      check should be (42)
    }
    
    it ("should work with multiple callbacks") {
      val wch = new WriteCallbackHelper[Int]{}
      var check = 0
      val cb = wch writeSubscribe {check = _}
      var check2 = 0
      val cb2 = wch writeSubscribe {in => check2 = in + 1}
      wch callWrites 10
      check should be (10)
      check2 should be (11)
      cb update {in => check = in*2}
      wch callWrites 21
      check should be (42)
      check2 should be (22)
      cb.detach
      wch callWrites 9*6
      check should be (42)
      check2 should be (9*6+1)
    }
  }
}
