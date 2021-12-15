package day

import java.util.*

data class Node(val x: Int, val y: Int, val cost: Int)

class Day15(part: Int) : DayBase(15, part) {
  var grid = if (part == 1) as2dIntArray() else addPart2(as2dIntArray())
  val toSearch = PriorityQueue<Node>(compareBy { n -> n.cost })
  val maxX = grid[0].size - 1
  val maxY = grid.size - 1
  var visited = Array(grid[0].size, { IntArray(grid.size, { Int.MAX_VALUE }) })

  override fun run() {

    toSearch.add(Node(0, 0, 0))
    var done = false
    while (!done) {
      val next = toSearch.poll()
      if (next != null) {
        // println("checking $next")
        done = search(next)
        if (done) {
          println(next.cost)
        }
      } else {
        done = true
      }
    }
  }

  fun addPart2(input: List<List<Int>>): List<List<Int>> {
    var output = input.map { y -> (0..4).toList().flatMap { i -> y.map { x -> i + x }.map { if (it > 9) it - 9 else it  } } }
    output = (0..4).toList().flatMap { i -> output.map { y -> y.map { x -> x + i }.map { if (it > 9) it - 9 else it  } } }
    return output
  }

  fun search(n: Node): Boolean {
    if (n.y == maxY && n.x == maxX) {
      return true
    }
    addNeighbors(n)
    return false
  }

  fun isBetter(n: Node): Boolean {
    if (n.cost < visited[n.x][n.y]) {
      visited[n.x][n.y] = n.cost
      return true
    }
    return false
  }

  fun addNeighbors(n: Node) {
    val neighbors = listOf(0 to -1, 0 to 1, 1 to 0, -1 to 0)
      .map { (x, y) -> n.x + x to n.y + y }
      .filter { (x, y) -> y in 0..maxY && x in 0..maxX }
      .map { (x, y) -> Node(x, y, grid[y][x] + n.cost) }
      .filter(::isBetter)
    toSearch.addAll(neighbors)
  }
}