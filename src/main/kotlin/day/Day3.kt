package day

import kotlin.math.roundToInt

class Day3(part: Int): DayBase(3, part) {
    override fun run() {
        val lines = as2dArray().map { line -> line.map { it.toInt()}}
        val sequences = buildSequences(lines)
        val gammaBinary = sequences.map { listMajority(it) }
        val epsilonBinary = sequences.map { listMinority(it) }
        if (part == 1) {
            val gamma = binaryToInt(gammaBinary)
            val epsilon = binaryToInt(epsilonBinary)
            println(gamma * epsilon)
        } else {
            val oxygenRating = binaryToInt(filterLifeSupport(lines, 0, ::listMajority))
            println(oxygenRating)
            val co2Rating = binaryToInt(filterLifeSupport(lines, 0, ::listMinority))
            println(co2Rating)
            println(oxygenRating * co2Rating)
        }
    }

    private fun buildSequences(lines: List<List<Int>>): List<MutableList<Int>> {
        val sequences = List<MutableList<Int>>(lines[0].size) { mutableListOf() }
        for (line in lines) {
            line.forEachIndexed { index, value -> sequences[index].add(value) }
        }
        return sequences
    }

    private fun filterLifeSupport(lines: List<List<Int>>, digitIndex: Int, valueFinder: (List<Int>) -> Int) : List<Int> {
        val filterValue = valueFinder(lines.map { it[digitIndex] })
        val remainingLines = lines.filter { it[digitIndex] == filterValue }
        if (remainingLines.size == 0) {
            println("remaining: ${lines.joinToString("\n") }}, digitIndex: $digitIndex, value: $filterValue")
            return listOf(0)
        }
        return if (remainingLines.size == 1) remainingLines[0]
            else filterLifeSupport(remainingLines, digitIndex + 1, valueFinder)
    }

    private fun listMajority(list: List<Int>): Int {
        val half = (list.size / 2.0).roundToInt()
        val sum = list.sum()
        return if (sum >= half) 1 else 0
    }

    private fun listMinority(list: List<Int>) : Int {
        return 1 - listMajority(list)
    }
}