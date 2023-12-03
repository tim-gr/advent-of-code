package util

import util.io.println
import util.io.readInputFile

fun main() {
    val input = readInputFile(year = 2015, day = 1)
    DayXX.task1(input).println()
    DayXX.task2(input).println()

    // Examples
    DayXX.task1(listOf("")).println()
    DayXX.task2(listOf("")).println()
}

object DayXX {

    fun task1(input: List<String>): Int {
        return 0
    }

    fun task2(input: List<String>): Int {
        return 0
    }
}
