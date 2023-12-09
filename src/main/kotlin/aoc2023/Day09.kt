package aoc2023

import util.io.println
import util.io.readExampleInputFile
import util.io.readInputFile

fun main() {
    val input = readInputFile(year = 2023, day = 9)
    Day09.task1(input).println()
    Day09.task2(input).println()

    // Examples
    Day09.task1(readExampleInputFile(year = 2023, day = 9)).println()
    Day09.task2(readExampleInputFile(year = 2023, day = 9)).println()
}

object Day09 {

    fun task1(input: List<String>) = input.sumOf { line -> getHistoryRows(line).sumOf { it.last() } }

    fun task2(input: List<String>) = input.sumOf { line ->
        getHistoryRows(line).dropLast(1).foldRight(0L) { numbers, acc -> numbers[0] - acc }
    }

    private fun getHistoryRows(line: String): List<List<Long>> {
        val historyRows = mutableListOf(line.split(" ").map { it.toLong() })
        while (historyRows.last().any { it != 0L }) {
            historyRows.add(historyRows.last().zipWithNext { first, second -> second - first })
        }
        return historyRows
    }
}
