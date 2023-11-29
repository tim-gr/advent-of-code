package aoc2023

import util.io.println
import util.io.readInputFile

fun main() {
    val input = readInputFile(year = 2023, day = 1)
    Day01.task1(input).println()
    Day01.task2(input).println()

    // Examples
    Day01.task1(listOf("")).println()
}

object Day01 {

    fun task1(input: List<String>): Int {
        return 0
    }

    fun task2(input: List<String>): Int {
        return 0
    }
}
