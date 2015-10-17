package com.stephenn

import org.specs2.mutable.Specification

class ModelSpec extends Specification {
  "model" should {
    "convert Location from meters" in {
      val a = Location.parse(123, 345, Meter)
      a.x === 123
      a.y === 345
    }

    "convert Location from kilometers" in {
      val a = Location.parse(1234, 3456, KiloMeter)
      a.x === 1
      a.y === 3
    }

    "convert Location from miles" in {
      val a = Location.parse(1, 2, Mile)
      a.x === 1610
      a.y === 3220
    }

    "convert location to miles" in {
      Location(10000, 10000).inMiles === ((6.2137, 6.2137): (Double, Double))
    }

    "orders Temperature" in {
      TemperatureOrdering.min(Temperature(1), Temperature(2)) === Temperature(1)
    }
  }
}