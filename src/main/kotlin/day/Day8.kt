package day

class Day8(part: Int) : DayBase(8, part) {
  val segmentCounts = mapOf(7 to 8, 2 to 1, 4 to 4, 3 to 7)

  override fun run() {
    val lines = asArray()
    if (part == 1) {
      var count = 0
      for (line in lines) {
        val (signalPatterns, outputValue) = line.split(" | ").map { it.split(" ") }
        count += outputValue.count { segmentCounts.containsKey(it.length) }
      }
      println(count)
    } else {

      var total = 0L

      for (line in lines) {
        val (signalPatterns, outputValues) = line.split(" | ").map { it.split(" ") }
        val (allSignalPatterns) = line.split(" | ")
        val one = signalPatterns.find { it.length == 2 }!!
        val four = signalPatterns.find { it.length == 4 }!!
        val seven = signalPatterns.find { it.length == 3 }!!
        val eight = signalPatterns.find { it.length == 7 }!!

        var pairs = allSignalPatterns
          .replace(" ", "")
          .groupBy { it }
          .map { it.value.count() to it.key  }

        /*
        b: 6, 4
        c: 8, 4
        d: 7, 5
        e: 4, 3
        f: 9, 5
        g: 7 (only in 8)
         */

        val a = seven.map { it }.minus(one.toSet()).first()
        val d = pairs.first { it.first == 7 && four.contains(it.second) }.second
        val g = pairs.first { it.first == 7 && !four.contains(it.second) }.second
        val signalCounts = pairs.filterNot { it.first == 7 || it.second == a }.toMap()

        val b = signalCounts[6]!!
        val c = signalCounts[8]!!
        val e = signalCounts[4]!!
        val f = signalCounts[9]!!

        val signalValues = mapOf(
          one.toSet() to 1,
          setOf(a, c, d, e, g) to 2,
          setOf(a, c, d, f, g) to 3,
          four.toSet() to 4,
          setOf(a, b, d, f, g) to 5,
          setOf(a, b, d, e, f, g) to 6,
          seven.toSet() to 7,
          eight.toSet() to 8,
          setOf(a, b, c, d, f, g) to 9,
          setOf(a, b, c, e, f, g) to 0
        )

        var finalOutputValue = 0

        //println("adding all $outputValues using $signalValues")
        for (outputValue in outputValues) {
          finalOutputValue *= 10
          val value = signalValues[outputValue.toSet()]
          if (value != null) {
            finalOutputValue += value
          } else {
            println("error on $line, missing $outputValue")
          }
        }
        //println("adding $finalOutputValue")
        total += finalOutputValue
      }
      println(total)
    }
  }
}