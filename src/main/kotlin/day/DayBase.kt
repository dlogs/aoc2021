package day

import java.io.File

abstract class DayBase(val day: Int, val part: Int) {
  val inputFileName = "inputs/$day.txt"

  val dailyFileLines get() = File(inputFileName).readLines()

  abstract fun run()

  fun asArray() = dailyFileLines.filter { it.isNotEmpty() }

  fun as2dArray() = asArray().map { line -> line.map { c -> c.toString() } }

  fun asArrayByBlanks(): List<List<String>> {
    val lines = dailyFileLines
    val blocks = mutableListOf<MutableList<String>>()
    var currentBlock = mutableListOf<String>()

    for (line in lines) {
      if (line.isBlank()) {
        blocks.add(currentBlock)
        currentBlock = mutableListOf()
      } else {
        currentBlock.add(line)
      }
    }
    return blocks
  }

  fun asIntArray() = asArray().map { it.toInt() }

  fun as2dIntArray() = asArray().map { line -> line.map { c -> c.toString().toInt() } }

  protected fun binaryToInt(binary: List<Int>) = Integer.parseInt(binary.joinToString("") { it.toString() }, 2)
}