package day

data class Dot(val x: Int, val y: Int)
data class Fold(val dir: String, val line: Int)

class Day13(part: Int) : DayBase(13, part) {
  val inputs = asArrayByBlanks()
  val dots = inputs[0].map { it.split(",") }.map { Dot(it[0].toInt(), it[1].toInt())}
  val folds = inputs[1]
    .map { it.split(" ").last().split("=") }
    .map { Fold(it[0], it[1].toInt())}
  val paper = buildPaper()

  override fun run() {
    if (part == 1) {
      foldAlong(folds.first())
    }
    if (part == 2) {
      folds.forEach(::foldAlong)
      println(paper.toString().replace("], [", "]\n[").replace("true", "#").replace("false", "."))
    }
    println(paper.sumOf { y -> y.count { it } })
  }

  fun buildPaper(): MutableList<MutableList<Boolean>> {
    val maxX = dots.maxOf { it.x }
    val maxY = dots.maxOf { it.y }
    val result = MutableList(maxY + 1){ MutableList(maxX + 1) { false } }
    for (dot in dots) {
      result[dot.y][dot.x] = true
    }
    return result
  }

  fun foldAlong(fold: Fold) {
    if (fold.dir == "x") {
      for (y in paper) {
        for (x in (y.size - 1 - fold.line) downTo 1) {
          val from = fold.line + x
          val to = fold.line - x
          y[to] = y[to] || y[from]
          y.removeAt(from)
        }
        y.removeAt(y.size - 1)
      }
    } else {
        for (y in (paper.size - 1 - fold.line) downTo 1) {
          val from = fold.line + y
          val to = fold.line - y
          for (x in paper[from].indices) {
            paper[to][x] = paper[to][x] || paper[from][x]
          }
          paper.removeAt(from)
        }
      paper.removeAt(paper.size -1)
    }
  }
}