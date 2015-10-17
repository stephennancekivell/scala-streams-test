package com.stephenn

import scala.util.Random
import java.time.LocalDateTime
import java.time.ZoneOffset

object Gen extends Generator {
  val rand = new Random()
}

trait Generator {
  val rand: Random

  def orCorrupt[A](a: A): String = {
    rand.nextInt(10) < 7 match {
      case true => a.toString()
      case false => genCorruptString()
    }
  }

  def row(): Row = {
    Row(localDateTime(), location(), temperature(), observatory())
  }

  case class Row(date: LocalDateTime, location: Location, temperature: Temperature, observatory: Observatory)

  def maybeCorruptRow(): String = {
    // like 1988-01-19T00:03:06.000000001|88,97|IJ7hbqOIsA|4iWta9qh1y"
    val r = row()

    val d = orCorrupt(r.date)
    val l = orCorrupt(r.location.render)
    val t = orCorrupt(r.temperature.render)
    val o = orCorrupt(r.observatory.render)

    s"$d|$l|$t|$o"
  }

  def localDateTime(): LocalDateTime = {
    LocalDateTime.ofEpochSecond(rand.nextInt(999999999), 1, ZoneOffset.UTC)
  }

  def genCorruptString(): String =
    rand.alphanumeric.take(10).mkString("")

  def location(): Location = {
    Location.parse(rand.nextInt(125), rand.nextInt(125), distanceUnit())
  }

  def distanceUnit(): DistanceUnit = {
    choose(DistanceUnit.units)
  }

  def temperature(): Temperature = {
    Temperature(rand.nextInt(1000))
  }

  def temperatureUnit(): TemperatureUnit = {
    choose(Temperature.units)
  }

  def choose[A](xs: Seq[A]) = {
    xs(rand.nextInt(xs.length))
  }

  def observatory(): Observatory = {
    choose(Observatory.observatories)
  }
}

object FileGenerator {
  import java.io.File

  def makeFile(f: File, noLines: Int): File = {
    printToFile(f) { writer =>
      Range(1, noLines).foreach { _ =>
        writer.println(Gen.maybeCorruptRow())
      }
    }
    f
  }

  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try { op(p) } finally { p.close() }
  }
}