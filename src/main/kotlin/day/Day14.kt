package day

class Day14(part: Int) : DayBase(14, part) {
  val inputs = asArrayByBlanks()
  val template = inputs[0][0]
  val start = template.first()
  val end = template.last()
  var polymer = buildInitialPolymer()

  val pairInsertions = inputs[1]
    .map { it.split(" -> ") }
    .associate { (from, to) -> from to to }

  override fun run() {
    val iterations = if (part == 1) 10 else 40
    for (i in 1..iterations) {
      polymer = nextPolymer()
    }
    val counts = polymer
      .flatMap { (pair, count) -> listOf(pair[0] to count, pair[1] to count) }
      .plus(listOf(start to 1L, end to 1L))
      .groupBy({ it.first }, { it.second })
      .map { (pair, counts) -> pair to counts.sum() / 2L }
      .sortedBy { it.second }
    val min = counts.first()
    val max = counts.last()
    println(max.second - min.second)
  }

  fun buildInitialPolymer(): Map<String, Long> {
    return template
      .windowed(2, 1)
      .groupingBy { it }
      .eachCount()
      .map { (pair, count) -> pair to count.toLong() }
      .toMap()
  }

  fun nextPolymer(): Map<String, Long> {
    return polymer
      .flatMap { (pair, count) ->
        listOf(pair[0] + pairInsertions[pair]!! to count, pairInsertions[pair] + pair[1] to count)
      }
      .groupBy({ it.first }, { it.second })
      .map { (pair, counts) -> pair to counts.sum() }
      .toMap()
  }
}