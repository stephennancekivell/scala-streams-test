package com.stephenn

import scala.io.Source
import scala.util.{Try, Failure, Success}
import java.time.LocalDateTime
import java.io.File

class SplitLineException(line: String) extends Exception {}

object Parse {
  def fromFile(f: File): Iterator[Row] = {
    Source.fromFile(f).getLines().flatMap { line =>
      fromLine(line).toOption
    }
  }

  def fromLine(line: String): Try[Row] = {
    for {
      (timeString, locationString, tempString, obsString) <- splitLine(line)
      obs = parseObservatory(obsString)
      time <- parseDate(timeString)
      location <- parseLocation(obs, locationString)
      temp <- parseTemperature(obs, tempString)
    } yield {
      Row(time, location, temp, obs)
    }
  }

  def splitLine(line: String): Try[(String,String,String,String)] = {
    line.split("\\|") match {
      case Array(a,b,c,d) =>
        Success((a,b,c,d))
      case o =>
        Failure(new SplitLineException(line))
    }
  } 

  def parseDate(d: String): Try[LocalDateTime] = {
    Try(LocalDateTime.parse(d))
  }

  def parseObservatory(o: String): Observatory = {
    Observatory.strToObservatory(o)
  }

  def parseLocation(o: Observatory, l: String): Try[Location] = {
    Try {
      val xy = l.split(",")
      Location.parse(xy(0).toInt, xy(1).toInt, o.distanceUnit)
    }
  }

  def parseTemperature(o: Observatory, t: String): Try[Temperature] = {
    Try{
      Temperature.parse(t.toInt, o.temperatureUnit)
    }
  }
}