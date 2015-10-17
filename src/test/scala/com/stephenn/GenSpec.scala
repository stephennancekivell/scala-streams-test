package com.stephenn

import org.specs2.mutable.Specification

import scala.util.Random

class GenSpec extends Specification {
  class StaticGenerator extends Generator {
    val rand = new Random(1) // fixed seed
  }

  "Gen" should {
    "make a observatory" in {
      val found = Gen.observatory
      Observatory.observatories.contains(found) === true
    }

    "make a string that looks currupt" in {
      val g = new StaticGenerator()
      g.genCorruptString() === "NAvZuGESoI"
    }

    "chooses" in {
      val g = new StaticGenerator()
      g.distanceUnit() === Mile
      g.distanceUnit() === Meter
      g.distanceUnit() === Meter
      g.distanceUnit() === Mile
      g.distanceUnit() === KiloMeter
      g.distanceUnit() === Meter
      g.distanceUnit() === KiloMeter
    }

    "Gen a line" in {
      val g = new StaticGenerator()
      g.maybeCorruptRow() === "1988-01-19T00:03:06.000000001|141680,156170|IJ7hbqOIsA|4iWta9qh1y"
      g.maybeCorruptRow() === "kraBq7ZFYe|IN8pKbyLI3|363|OTHER"
      g.maybeCorruptRow() === "K3Q3sDEuVs|41860,178710|598|yiFctJPfRA"
    }
  }
}