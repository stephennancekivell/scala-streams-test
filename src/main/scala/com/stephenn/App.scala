package com.stephenn

import java.io.File

import com.stephenn.Stats.Results

object App {

  def main(args: Array[String]): Unit = {
    args.headOption match {
      case Some("gen") => gen()
      case Some("parse") => parse()
      case Some(c) =>
        println("unknown command: "+c)
      case None =>
        println(usage)
    }
  }

  val usage =
    """
      |options:
      | gen     generate a test data file data.txt
      | parse   parse a test data file data.txt
    """.stripMargin

  def parse(): Unit = {
    val data = Parse.fromFile(new File("data.txt"))
    val results = Stats.all(data)

    println(show(results))
  }

  def show(results: Results): String = {
    s"""
      | min temperature: ${results.minTemperature}
      | max temperature: ${results.maxTemperature}
      | number of observations per station: ${results.noObservations}
    """.stripMargin
  }

  def gen(): Unit = {
    FileGenerator.makeFile(new File("data.txt"), 10000000)
  }
}