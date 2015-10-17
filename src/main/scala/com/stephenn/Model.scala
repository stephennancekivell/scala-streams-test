package com.stephenn

import java.time.LocalDateTime

sealed trait DistanceUnit
case object Mile extends DistanceUnit
case object Meter extends DistanceUnit
case object KiloMeter extends DistanceUnit
object DistanceUnit {
  val units = Seq(Mile, Meter, KiloMeter)
}

case class Location(x: Int, y: Int) {
  // location stored in meters

  lazy val km = (x/1000, y/1000)
  lazy val inMiles = (x * Location.metersToMiles, y * Location.metersToMiles)
  lazy val inMeters = (x,y)

  def render(): String = s"$x,$y"
}

object Location {
  def parse(x: Int, y: Int, unit: DistanceUnit): Location = {
    unit match {
      case Meter =>
        new Location(x,y)
      case KiloMeter =>
        new Location(x/1000, y/1000)
      case Mile =>
        new Location(x*1610, y*1610)
    }
  }

  private val metersToMiles = 0.00062137

  def distance(a: Location, b: Location): Double = {
    Math.sqrt((a.x - b.x)^2 + (a.y-b.y)^2)
  }
}

sealed trait TemperatureUnit
case object Celsius extends TemperatureUnit
case object Fahrenheit extends TemperatureUnit
case object Kelvin extends TemperatureUnit

case class Temperature(v: Int){
  // stored in Celsius

  def celsius: Int = v
  def fahrenheit: Int = ???
  def kelvin: Int = ???

  def render(): String = s"$v"
}

object TemperatureOrdering extends Ordering[Temperature] {
  def compare(t1: Temperature, t2: Temperature) = t1.v - t2.v
}

object Temperature {
  val units = Seq(Celsius, Fahrenheit, Kelvin)

  def parse(i: Int, unit: TemperatureUnit): Temperature = {
    unit match {
      case Celsius => Temperature(i)
      case Fahrenheit => Temperature(fahrenheit2Celsius(i))
      case Kelvin => Temperature(kelvin2Celsius(i))
    }
  }

  def celsius2Fahrenheit(i: Int): Int = (i*2)+30
  def fahrenheit2Celsius(i: Int): Int = ((i-32.0)*(5.0/9.0)).toInt
  def kelvin2Celsius(i: Int): Int = (i-273)
  
}

sealed trait Observatory {
  def render: String
  def temperatureUnit: TemperatureUnit
  def distanceUnit: DistanceUnit
}
case object AU extends Observatory {
  val render = "AU"
  val temperatureUnit = Celsius
  val distanceUnit = KiloMeter
}
case object US extends Observatory {
  val render = "US"
  val temperatureUnit = Fahrenheit
  val distanceUnit = Mile
}
case object FR extends Observatory {
  val render = "FR"
  val temperatureUnit = Kelvin
  val distanceUnit = Meter
}
case object Other extends Observatory {
  val render = "OTHER"
  val temperatureUnit = Kelvin
  val distanceUnit = KiloMeter
}

object Observatory {
  val observatories = Seq(AU, US, FR, Other)
  val strToObservatory = Map(
    "AU" -> AU,
    "US" -> US,
    "FR" -> FR)
    .withDefaultValue(Other)
}

case class Row(date: LocalDateTime, location: Location, temperature: Temperature, observatory: Observatory)

