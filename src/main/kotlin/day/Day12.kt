package day

class Day12(part: Int) : DayBase(12, part) {
  val lines = asArray()
  val touching = mutableMapOf<String, MutableList<String>>()

  override fun run() {
    for (line in lines) {
      val (from, to) = line.split("-")
      if (touching[from] == null) {
        touching[from] = mutableListOf()
      }
      if (touching[to] == null) {
        touching[to] = mutableListOf()
      }
      touching[to]?.add(from)
      touching[from]?.add(to)
    }
    println(touching["start"]!!.sumOf {followPath(it, setOf(), part == 1) })
  }

  fun followPath(path: String, visited: Set<String>, usedDouble: Boolean): Int {
    println("trying $path")
    if (path == "end") return 1
    if (path == "start") return 0
    val isSmall = path[0].isLowerCase()
    var usingDouble = usedDouble
    if (isSmall && visited.contains(path)) {
      if (usedDouble) return 0
      else usingDouble = true
    }

    return touching[path]!!.sumOf { followPath(it, visited.plus(path), usingDouble) }
  }
}