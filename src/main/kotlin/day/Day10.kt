package day

import java.util.*

class Day10(part: Int): DayBase(10, part) {
    override fun run() {
        val lines = asArray()
        var score = 0L
        if (part == 1) {
            for (line in lines) {
                score += when (findInvalid(line)) {
                    ")" -> 3
                    "]" -> 57
                    "}" -> 1197
                    ">" -> 25137
                    else -> 0
                }
            }
        } else {
            val lineScores = mutableListOf<Long>()
            val incompleteLines = lines.filter { findInvalid(it) == null }
            for (line in incompleteLines) {
                val missingChars = findMissing(line)
                var lineScore = 0L
                for (char in missingChars) {
                    lineScore *= 5
                    lineScore += when (char) {
                        ")" -> 1
                        "]" -> 2
                        "}" -> 3
                        ">" -> 4
                        else -> throw Exception("wrong char")
                    }
                }
                // println("${missingChars.joinToString("") }: $lineScore")
                lineScores.add(lineScore)
            }
            lineScores.sort()
            score = lineScores[lineScores.size / 2]
        }

        println(score)
    }

    val pairs = mapOf("(" to ")", "[" to "]", "{" to "}", "<" to ">")

    fun findInvalid(line: String): String? {
        val stack = Stack<String>()
        for (char in line) {
            val value = char.toString()
            if (pairs.containsKey(value)) stack.push(value)
            else if (pairs[stack.pop()] != value) return value
        }
        return null
    }

    fun findMissing(line: String): List<String> {
        val stack = Stack<String>()
        for (char in line) {
            val value = char.toString()
            if (pairs.containsKey(value)) stack.push(value)
            else if (pairs[stack.pop()] != value) throw Exception("invalid line")
        }
        return stack.map { pairs[it]!! }.asReversed()
    }
}