package test.scala

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers
import main.scala.util._

class EventHelperSpec extends Spec with ShouldMatchers {

  describe("An EventHelper") {

    class EventHelperTest extends EventHelper(){

    }
    val evhelper = new EventHelperTest()

    it("should be able to add events") {
      
    }

    it("should be able to remove events") {

    }
  }
}

