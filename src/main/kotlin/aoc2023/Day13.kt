package aoc2023

import util.io.println
import util.io.readExampleInputFile
import util.io.readInputFile

fun main() {

    val input = readInputFile(year = 2023, day = 13)
    Day13.task1(input).println()
    Day13.task2(input).println()

    // Examples
    val exampleInput = readExampleInputFile(year = 2023, day = 13)
    Day13.task1(exampleInput).println()
    Day13.task2(exampleInput).println()
}

object Day13 {

    fun task1(input: List<String>) = calculateMirror(input, differences = 0)

    fun task2(input: List<String>) = calculateMirror(input, differences = 1)

    private fun calculateMirror(input: List<String>, differences: Int): Int {
        val patterns = input.fold(mutableListOf<MutableList<String>>(mutableListOf())) { acc, line ->
            if (line.isBlank()) acc.add(mutableListOf()) else acc.last().add(line)
            acc
        }

        return patterns.sumOf { pattern ->

            val columns = mutableListOf<String>()
            for (x in pattern[0].indices) {
                columns.add(pattern.fold("") { acc, row -> acc + row[x] })
            }

            val mirroredRows = countLinesBeforeMirror(pattern, differences)
            val mirroredColumns = countLinesBeforeMirror(columns, differences)

            mirroredRows * 100 + mirroredColumns
        }
    }

    private fun countLinesBeforeMirror(list: List<String>, differences: Int): Int {
        return list.zipWithNext().withIndex()
            .filter { it.value.findNumberOfDifferences() <= differences }
            .map { Pair(list.take(it.index + 1).reversed(), list.takeLast(list.size - it.index - 1)) }
            .find { it.first.zip(it.second).sumOf { zipped -> zipped.findNumberOfDifferences() } == differences }
            ?.first?.size ?: 0
    }

    private fun Pair<String, String>.findNumberOfDifferences() = first.zip(second).count { it.first != it.second }
}
