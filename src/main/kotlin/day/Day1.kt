package day

class Day1(part: Int) : DayBase(1, part) {
  override fun run() {
    if (part == 1) {
      println(asIntArray().windowed(2, 1).count { it[0] < it[1] })
    } else {
      val sums = asIntArray()
        .windowed(3, 1)
        .map { it.sum() }
        .windowed(2, 1)
        .count { it[0] < it[1] }
      println(sums)
    }
  }
}