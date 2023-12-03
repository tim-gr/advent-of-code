package aoc2023

import util.io.println
import util.io.readInputFile

fun main() {
    val input = readInputFile(year = 2023, day = 1)
    Day01.task1(input).println()
    Day01.task2(input).println()

    // Examples
    Day01.task1(listOf("a1b2c3d4e5f")).println()
    Day01.task2(listOf("aone2c3d4e5f")).println()
}

object Day01 {

    fun task1(input: List<String>): Int {
        return input.sumOf { string ->
            ("" + string.first { it.isDigit() } + string.last { it.isDigit() }).toInt()
        }
    }

    fun task2(input: List<String>): Int {
        return input.sumOf { string ->
            (findFirstNumber(string).toNumber() + findFirstNumber(string, reversed = true).toNumber()).toInt()
        }
    }

    private fun findFirstNumber(string: String, reversed: Boolean = false): String {
        for (i in if (reversed) string.indices.reversed() else string.indices) {
            for (number in NUMBERS) {
                if (string.substring(i).startsWith(number)) {
                    return number
                }
            }
        }
        error("No number found")
    }

    private val NUMBERS = listOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    )
    private fun String.toNumber(): String = (NUMBERS.indexOf(this) % 9 + 1).toString()
}
