package aoc2016

import util.io.println
import util.io.readInputFile

fun main() {
    val input = readInputFile(year = 2016, day = 2)
    Day02.task1(input).println()
    Day02.task2(input).println()
}

object Day02 {

    fun task1(input: List<String>): String {
        var value = 5
        var result = ""
        input.forEach {
            it.forEach { symbol ->
                value = when (symbol) {
                    'U' -> if (value - 3 < 1) value else value - 3
                    'D' -> if (value + 3 > 9) value else value + 3
                    'R' -> if (value % 3 == 0) value else value + 1
                    'L' -> if (value % 3 == 1) value else value - 1
                    else -> error("Invalid input: $symbol")
                }
            }
            result += value
        }
        return result
    }

    fun task2(input: List<String>) = 0
}
