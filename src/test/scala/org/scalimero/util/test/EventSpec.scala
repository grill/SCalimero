package scalimero.util.test

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers
import scalimero.util._

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
  }
}

