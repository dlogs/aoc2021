package day

class Day4(part: Int) : DayBase(4, part) {
  val inputs = asArrayByBlanks()
  val numsToDraw = inputs[0][0].split(",").map { it.toInt() }
  val boards = inputs.drop(1).map(::buildBoard)

  override fun run() {
    val drawn = mutableSetOf<Int>()
    for (num in numsToDraw) {
      drawn.add(num)
      val winningBoards = boards.filter { b -> b.any { s -> drawn.containsAll(s) } }
      if (part == 1) {
        if (winningBoards.isNotEmpty()) {
          val winningBoard = winningBoards.first()
          val uncalled = winningBoard.take(5).sumOf { s -> s.subtract(drawn).sum() }
          println(uncalled * num)
          break
        }
      } else {
        if (winningBoards.size == boards.size) {
          val drawnBefore = drawn.subtract(setOf(num))
          val lastWinningBoard = boards.filterNot { b -> b.any { s -> drawnBefore.containsAll(s) } }.first()
          val uncalled = lastWinningBoard.take(5).sumOf { s -> s.subtract(drawn).sum() }
          println(uncalled * num)
          break
        }
      }
    }
  }

  fun buildBoard(inputBoard: List<String>): List<Set<Int>> {
    val numberBoardsTemp = inputBoard.map { it.trim().split(Regex(" +")) }

    val numberBoards = numberBoardsTemp.map { it.map { n -> n.toInt() }}
    val boards = mutableListOf<Set<Int>>()
    // add rows
    boards.addAll(numberBoards.map { it.toSet() })
    // add columns
    boards.addAll((0..4).map { x -> numberBoards.map { b -> b[x] }.toSet() })
    println(boards)
    return boards
  }
}