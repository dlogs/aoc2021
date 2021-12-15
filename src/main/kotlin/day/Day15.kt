package day

import java.util.*

data class Node(val x: Int, val y: Int, val cost: Int)

class Day15(part: Int) : DayBase(15, part) {
  var grid = as2dIntArray()
  val toSearch = PriorityQueue<Node>(compareBy { n -> n.cost })
  var visited = List(grid[0].size, { MutableList<Int?>(grid.size, { null }) })

  override fun run() {
    if (part == 2) {
      addPart2()
      //println(grid.joinToString("\n") { it.joinToString("") } )
      visited = List(grid[0].size, { MutableList<Int?>(grid.size, { null }) })
    }

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

  fun addPart2() {
    grid = grid.map { y -> (0..4).toList().flatMap { i -> y.map { x -> i + x }.map { if (it > 9) it - 9 else it  } } }
    grid = (0..4).toList().flatMap { i -> grid.map { y -> y.map { x -> x + i }.map { if (it > 9) it - 9 else it  } } }
  }

  fun search(n: Node): Boolean {
    if (n.y == grid.size - 1 && n.x == grid[0].size - 1) {
      return true
    }
    if (hasBetter(n)) {
      return false
    }
    addNeighbors(n)
    return false
  }

  fun hasBetter(n: Node): Boolean {
    val visitedWithCost = visited[n.x][n.y]
    val lowerCost = (visitedWithCost == null || n.cost <= visitedWithCost)
    if (lowerCost) {
      visited[n.x][n.y] = n.cost
    }
    return !lowerCost
  }

  fun addNeighbors(n: Node) {
    val neighbors = listOf(0 to -1, 0 to 1, 1 to 0, -1 to 0)
      .map { (x, y) -> n.x + x to n.y + y }
      .filter { (x, y) -> y >= 0 && y < grid.size && x >= 0 && x < grid[0].size }
      .map { (x, y) -> Node(x, y, grid[y][x] + n.cost) }
      .filterNot(::hasBetter)
    toSearch.addAll(neighbors)
  }
}