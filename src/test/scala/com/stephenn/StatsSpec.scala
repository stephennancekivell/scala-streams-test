package com.stephenn

import java.time.LocalDateTime

import com.stephenn.Stats.Results
import org.specs2.mutable.Specification

class StatsSpec extends Specification {
  "Stats" should {
    val testRow = Row(LocalDateTime.parse("1988-01-19T00:03:06.000000001"),Location(88,97),Temperature(-150),FR)
    "parse a stream" in {
      val xs = Iterator(testRow, testRow.copy(temperature = Temperature(12)))

      Stats.all(xs) === Results(
        minTemperature = Temperature(-150),
        maxTemperature = Temperature(12),
        noObservations = Map(FR -> 2)
      )
    }
  }
}
