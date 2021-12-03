package day

data class Cmd(val direction: String, val value: Int) {
    companion object {
        fun create(line: String): Cmd {
            val (direction, strValue) = line.split(" ")
            return Cmd(direction, strValue.toInt())
        }
    }
}

class Day2(part: Int): DayBase(2, part) {
    override fun run() {
        val cmds = asArray().map { Cmd.create(it) }
        var hor: Long = 0
        var vert: Long = 0
        var aim: Long = 0
        if (part == 1) {
            for (cmd in cmds) {
                when (cmd.direction) {
                    "forward" -> hor += cmd.value
                    "down" -> vert += cmd.value
                    "up" -> vert -= cmd.value
                }
            }
        } else {
            for ((direction, value) in cmds) {
                when (direction) {
                    "forward" -> {
                        hor += value
                        vert += aim * value
                    }
                    "down" -> aim += value
                    "up" -> aim -= value
                }
            }
        }

        println(hor * vert)
    }
}