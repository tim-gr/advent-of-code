package aoc2023

import util.io.println
import util.io.readInputFile

fun main() {
    val input = readInputFile(year = 2023, day = 1)
    Day01.task1(input).println()
    Day01.task2(input).println()

    // Examples
    Day01.task1(listOf("a1b2c3d4e5f")).println()
}

object Day01 {

    fun task1(input: List<String>): Int {
        return input.sumOf { string ->
            ("" + string.first { it.isDigit() } + string.last { it.isDigit() }).toInt()
        }
    }

    fun task2(input: List<String>): Int {
        return input.sumOf { string ->
            (findFirstOccurrence(string).toNumber() + findLastOccurrence(string).toNumber()).toInt()
        }
    }

    private fun findFirstOccurrence(string: String): String {
        for (number in DIGITS) {
            if (string.startsWith(number)) {
                return number
            }
        }
        return findFirstOccurrence(string.substring(1))
    }

    private fun findLastOccurrence(string: String): String {
        for (number in DIGITS) {
            if (string.endsWith(number)) {
                return number
            }
        }
        return findLastOccurrence(string.substring(0, string.lastIndex))
    }

    private val DIGITS = listOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    )

    private fun String.toNumber(): String = when(this) {
        "one" -> "1"
        "two" -> "2"
        "three" -> "3"
        "four" -> "4"
        "five" -> "5"
        "six" -> "6"
        "seven" -> "7"
        "eight" -> "8"
        "nine" -> "9"
        else -> this
    }
}
