package aoc2023

import util.io.println
import util.io.readExampleInputFile
import util.io.readInputFile

// This time I needed to look into other peoples' code, especially:
// https://github.com/eagely/adventofcode/blob/main/src/main/kotlin/solutions/y2023/Day12.kt
// Improved my knowledge about recursion and dynamic programming.
fun main() {
    val input = readInputFile(year = 2023, day = 12)
    Day12.task1(input).println()
    Day12.task2(input).println()

    // Examples
    val exampleInput = readExampleInputFile(year = 2023, day = 12)
    Day12.task1(exampleInput).println()
    Day12.task2(exampleInput).println()
}

object Day12 {

    fun task1(input: List<String>) = input.sumOf { row ->
        val (springs, numbers) = row.split(" ")
        countAlternatives("$springs.", numbers.split(",").map { it.toInt() })
    }

    fun task2(input: List<String>) = input.sumOf { row ->
        val (springs, numbers) = row.split(" ")
        val newSprings = "${(0 until 5).joinToString("?") { springs }}."
        val newNumbers = (0 until 5).joinToString(",") { numbers }
        countAlternatives(newSprings, newNumbers.split(",").map { it.toInt() })
    }

    private val cache = mutableMapOf<Pair<String, List<Int>>, Long>()

    private fun countAlternatives(row: String, numbers: List<Int>): Long {
        // Handle final recursion states
        // -> 0 when no # anymore but numbers exists. 0 when numbers exist, but row is empty. 1 when valid.
        if (numbers.isEmpty()) return if ("#" in row) 0 else 1
        if (row.isEmpty()) return 0

        return cache.getOrPut(Pair(row, numbers)) {
            var result = 0L
            if (row[0] in ".?") {
                result += countAlternatives(row.drop(1), numbers)
            }
            if (row[0] in "#?") {
                val nextGroup = row.takeWhile { it in "?#" }
                // A valid group cannot be smaller than the next number. The spring after the group cannot be #
                if (numbers.first() <= nextGroup.length && row[numbers.first()] != '#') {
                    // numbers.first() + 1 -> plus one since there cannot be two working spring groups next to each other
                    result += countAlternatives(row.drop(numbers.first() + 1), numbers.drop(1))
                }
            }
            result
        }
    }
}
