package day

class Day6(part: Int) : DayBase(6, part) {
  var ages = asArray()[0]
    .split(",")
    .map { it.toInt() }
    .toMutableList()
    .groupingBy { it }
    .eachCount()
    .map { it.key to it.value.toULong() }
    .toMap()
    .toMutableMap()

  override fun run() {
    val limit = if (part == 1) 80 else 256
    println(ages)
    for (day in 1..limit) {
      for (i in 0..8) {
        ages[i - 1] = ages[i] ?: 0UL
      }
      val creating = ages[-1] ?: 0UL
      ages[6] = ages[6]!! + creating
      ages[8] = creating
      ages[-1] = 0UL
//      println(ages.toSortedMap())
//      println(ages.map { it.value }.sum())
    }
    println(ages.toSortedMap())
    println(ages.map { it.value }.sum())
  }
}