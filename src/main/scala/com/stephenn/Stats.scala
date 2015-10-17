package com.stephenn

object Stats {
  case class Results(
    minTemperature: Temperature,
    maxTemperature: Temperature,
    noObservations: Map[Observatory, Int]
    // mean temperature
    // distance traveled
  ) {
    def meanTemperature: Temperature = {
      // TODO
      // sum temperature for each one, then divide on way out.
    }
  }

  def meanTemperature(xs: Stream[Row]): Temperature = {
    val t = xs.map(_.temperature.v).sum / xs.length
    Temperature(t)
  }

  def firstResults(r: Row) = Results(
    minTemperature = r.temperature,
    maxTemperature = r.temperature,
    noObservations = Map(r.observatory -> 1)
  )

  def append(results: Results, row: Row): Results = {
    Results(
      minTemperature = TemperatureOrdering.min(results.minTemperature, row.temperature),
      maxTemperature = TemperatureOrdering.max(results.minTemperature, row.temperature),
      noObservations = results.noObservations.updated(row.observatory, results.noObservations.getOrElse(row.observatory, 0) + 1)
    )
  }

  def all(xs: Iterator[Row]): Results = {
    val head = xs.take(1).toSeq.head
    val initResults = firstResults(head)

    xs.drop(1).foldLeft(initResults)(append)
  }
}