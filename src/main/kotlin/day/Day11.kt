package day

class Day11(part: Int) : DayBase(11, part) {
  val rows = as2dIntArray().map { it.toMutableList() }
  var flashes = 0

  override fun run() {
    if (part == 1) {
      for (i in 1..100) {
        step()
      }

      println(flashes)
    } else {
      for (i in 1..1000) {
        flashes = 0
        step()
        if (flashes == 100) {
          println(i)
          break
        }
      }
    }
  }

  fun step() {
    forEachIndex { y, x -> incr(y, x) }
    resetFlashed()
  }

  fun incr(y: Int, x: Int) {
    if (y < 0 || x < 0 || y >= rows.size || x >= rows[y].size) return

    rows[y][x] += 1
    if (rows[y][x] == 10) {
      flash(y, x)
    }
  }

  fun flash(y: Int, x: Int) {
    flashes += 1

    incr(y - 1, x - 1)
    incr(y, x - 1)
    incr(y + 1, x - 1)

    incr(y - 1, x + 1)
    incr(y, x + 1)
    incr(y + 1, x + 1)

    incr(y - 1, x)
    incr(y + 1, x)
  }

  fun resetFlashed() {
    forEachIndex { y, x -> if (rows[y][x] > 9) rows[y][x] = 0 }
  }

  fun forEachIndex(evaluator: (y: Int, x: Int) -> Unit) {
    for (y in rows.indices) {
      for (x in rows[y].indices) {
        evaluator(y, x)
      }
    }
  }
}