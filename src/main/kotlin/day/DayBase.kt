package day

import java.io.File

abstract class DayBase(val day: Int, val part: Int) {
  val inputFileName = "inputs/$day.txt"

  val dailyFileLines get() = File(inputFileName).readLines()

  abstract fun run()

  fun asArray() = dailyFileLines.filter { it.isNotEmpty() }

  fun as2dArray() = asArray().map { line -> line.map { c -> c.toString() } }

  fun asArrayByBlanks(): List<List<String>> {
    val lines = asArray()
    return asArray().flatMapIndexed { index, x ->
      when {
        index == 0 || index == lines.lastIndex -> listOf(index)
        x.isEmpty() -> listOf(index - 1, index + 1)
        else -> emptyList()
      }
    }
      .windowed(size = 2, step = 2) { (from, to) -> lines.slice(from..to) }
  }

  fun asIntArray() = asArray().map { it.toInt() }

  fun as2dIntArray() = asArray().map { line -> line.map { c -> c.toString().toInt() } }

  protected fun binaryToInt(binary: List<Int>) = Integer.parseInt(binary.joinToString("") { it.toString() }, 2)
}