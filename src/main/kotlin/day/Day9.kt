package day

data class Coordinate(val y: Int, val x: Int)

class Day9(part: Int) : DayBase(9, part) {
  val lines = as2dIntArray()
  var checked = mutableSetOf<Coordinate>()

  override fun run() {
    if (part == 1) {
      val score = lines.flatMapIndexed { y, line ->
        line.filterIndexed { x, value -> isMin(value, y, x) }
      }.map { it + 1 }.sum()
      println(score)
    } else {
      val sizes = mutableListOf<Int>()
      lines.forEachIndexed { y, line ->
        line.forEachIndexed { x, value ->
          if (isMin(value, y, x)) {
            sizes.add(countSurrounding(y, x))
            checked.clear()
          }
        }
      }
      sizes.sort()
      println(sizes[sizes.size - 1] * sizes[sizes.size - 2] * sizes[sizes.size - 3])
    }
  }

  fun isMin(value: Int, y: Int, x: Int): Boolean {
    val surrounding = listOf(
      valueAt(y, x - 1),
      valueAt(y, x + 1),
      valueAt(y - 1, x),
      valueAt(y + 1, x)
    )
    return value < surrounding.minOf { it }
  }

  fun valueAt(y: Int, x: Int): Int {
    return if (x < 0 || y < 0 || x >= lines[0].size || y >= lines.size) {
      9
    } else {
      lines[y][x]
    }
  }

  fun countSurrounding(y: Int, x: Int): Int {
    if (valueAt(y, x) == 9) return 0
    val coordinate = Coordinate(y, x)
    if (checked.contains(coordinate)) return 0
    checked.add(coordinate)
    return arrayOf(
      countSurrounding(y, x - 1),
      countSurrounding(y, x + 1),
      countSurrounding(y - 1, x),
      countSurrounding(y + 1, x),
      1
    ).sumOf { it }
  }
}