package day

import kotlin.math.absoluteValue

data class VentLine(val from: Coord, val to :Coord)
data class Coord(val x: Int, val y: Int)

class Day5(part: Int) : DayBase(5, part) {
  val ventLines = asArray().map(::buildVentLine)
  val floor = MutableList(1000) { MutableList(1000) { 0 } }

  fun buildVentLine(inputLine: String): VentLine {
    val parts = inputLine.split(" -> ")
    val (from, to) = parts
    val (fromX, fromY) = from.split(",").map { it.toInt() }
    val (toX, toY) = to.split(",").map { it.toInt() }
    return VentLine(Coord(fromX, fromY), Coord(toX, toY))
  }

  override fun run() {
    for (ventLine in ventLines) {
      if ((ventLine.from.x != ventLine.to.x && ventLine.from.y != ventLine.to.y) && part == 1) continue

      //println("diagonal: $ventLine")
      val xStep = if (ventLine.from.x > ventLine.to.x) -1 else if (ventLine.to.x > ventLine.from.x) 1 else 0
      val yStep = if (ventLine.from.y > ventLine.to.y) -1 else if (ventLine.to.y > ventLine.from.y) 1 else 0
      val limit = arrayOf((ventLine.to.x - ventLine.from.x).absoluteValue, (ventLine.to.y - ventLine.from.y).absoluteValue).maxOrNull()!!
      println("going from ${ventLine.from} to ${ventLine.to} using ($xStep, $yStep), gap $limit")
      for (i in 0 .. limit) {
        floor[ventLine.from.y + (i * yStep)][ventLine.from.x + (i * xStep)]++
      }
    }

    println(floor.toString().replace("], [", "]\n["))
    println(floor.sumOf { x -> x.count { it >= 2 } })
  }
}