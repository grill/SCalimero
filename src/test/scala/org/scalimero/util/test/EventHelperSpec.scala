package org.scalimero.util.test

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers
import org.scalimero.util._

class EventHelperSpec extends Spec with ShouldMatchers {

  describe("An EventHelper") {
    describe("(Hello World event)") {
      var eh = new EventHelper[String] {
          val events = List(
            ({s:String => s == "hello"} -> "world")
          )
        }
      
      it ("should give an empty event list when given \"foo\"") {
        eh eventList "foo" should equal (Nil)
      }
      
      it ("should give the \"world\" event when given \"hello\"") {
        eh eventList "hello" should equal (List("world"))
      }
      
      var worldCalled = false
      
      it ("should allow subscriptions to the \"world\" event") {
        eh.subscribe("world"){worldCalled = true}
      }
      
      it ("should not allow subscriptions to the \"foo\" event") {
        evaluating {eh.subscribe("foo"){1+1}} should produce [NoSuchEventException]
      }
      
      it ("should call back properly") {
        eh callEvents "hello"
        worldCalled should be (true)
      }
    }
    
    describe("(Callback Management)") {
      val eh = new EventHelper[String] {
          val events = List(
            ({s:String => s == "hello"} -> "world")
          )
        }
      
      var worldCalled = 0
      var callback = eh.subscribe("world"){worldCalled += 1}
      
      it ("should call back properly") {
        eh callEvents "hello"
        worldCalled should be (1)
        eh callEvents "hello"
        worldCalled should be (2)
      }
      
      it ("should allow callback updates") {
        worldCalled = 0
        callback update {worldCalled += 2}
        eh callEvents "hello"
        worldCalled should be (2)
        eh callEvents "hello"
        worldCalled should be (4)
      }
      
      it ("should allow callback unsubscription") {
        worldCalled = 0
        eh unsubscribe callback
        eh callEvents "hello"
        worldCalled should be (0)
      }
      
      it ("should allow callbacks to unsubscribe themselves") {
        worldCalled = 0
        callback = eh.subscribe("world"){worldCalled += 1}
        callback.detach
        eh callEvents "hello"
        worldCalled should be (0)
      }
    }
    
    describe("with multiple callbacks and events") {
      it ("should work") {
        val eh = new EventHelper[Int] {
          var oldVal = 0
          val events = List(
            ({i:Int => i >= 10} -> ">=10"),
            ({i:Int => i == 10} -> "=10"),
            ({i:Int => i > 10} -> ">10"),
            ({i:Int => i < 10} -> "<10"),
            ({i:Int => val ret = i < oldVal; oldVal = i; ret} -> "<")
          )
        }
        var ge10 = false
        var ge102 = false
        var e10 = false
        var g10 = false
        var s10 = false
        var s = false
        eh.subscribe(">=10"){ge10 = true}
        val cb = eh.subscribe(">=10"){ge102 = true}
        eh.subscribe("=10"){e10 = true}
        eh.subscribe(">10"){g10 = true}
        eh.subscribe("<10"){s10 = true}
        eh.subscribe("<"){s = true}
        
        eh callEvents 15
        
        ge10 should be (true)
        ge102 should be (true)
        e10 should be (false)
        g10 should be (true)
        s10 should be (false)
        
        ge10 = false
        ge102 = false
        e10 = false
        g10 = false
        s10 = false
        s = false
        
        cb.detach
        eh callEvents 10
        
        ge10 should be (true)
        ge102 should be (false)
        e10 should be (true)
        g10 should be (false)
        s10 should be (false)
        s should be (true)
      }
    }
  }
}

