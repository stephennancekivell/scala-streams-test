package com.stephenn

import org.specs2.mutable.Specification
import scala.util.{Try, Failure, Success}
import java.time.LocalDateTime

class ParseSpec extends Specification {
  "Parse" should {
    "parse observatory" in {
      Parse.parseObservatory("cake") === Other
      Parse.parseObservatory("US") === US
    }

    "parse temperature" in {
      Parse.parseTemperature(Other, "cake").isFailure === true
      Parse.parseTemperature(Other, "1") === Success(Temperature(-272))
    }

    "parse Location" in {
      Parse.parseLocation(Other, "cake").isFailure === true
      Parse.parseLocation(Other, "1000,2000") === Success(Location(1,2))
    }

    "parse date" in {
      Parse.parseDate("1988-01-19T00:03:06") === Success(LocalDateTime.parse("1988-01-19T00:03:06"))
    }

    "from successful line" in {
      Parse.fromLine("1988-01-19T00:03:06.000000001|88,97|123|FR") ===
        Success(Row(LocalDateTime.parse("1988-01-19T00:03:06.000000001"),Location(88,97),Temperature(-150),FR))
    }
  }
}