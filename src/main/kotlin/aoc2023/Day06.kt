package aoc2023

import util.io.println
import util.io.readExampleInputFile
import util.io.readInputFile

fun main() {
    val input = readInputFile(year = 2023, day = 6)
    Day06.task1(input).println()
    Day06.task2(input).println()

    // Examples
    val exampleInput = readExampleInputFile(year = 2023, day = 6)
    Day06.task1(exampleInput).println()
    Day06.task2(exampleInput).println()
}

object Day06 {

    fun task1(input: List<String>): Int {
        val times = input[0].substringAfter("Time:").split(" ").filter { it.isNotEmpty() }
        val winningDistances = input[1].substringAfter("Distance:").split(" ").filter { it.isNotEmpty() }
        return calculateWinsProduct(times.map { it.toLong() }.zip(winningDistances.map { it.toLong() }))
    }

    fun task2(input: List<String>): Int {
        val time = input[0].substringAfter("Time:").replace(" ", "").toLong()
        val winningDistance = input[1].substringAfter("Distance:").replace(" ", "").toLong()
        return calculateWinsProduct(listOf(Pair(time, winningDistance)))
    }

    private fun calculateWinsProduct(input: List<Pair<Long, Long>>): Int {
        return input.map {
            val winning = mutableListOf<Long>()
            for (i in 1 until it.first) {
                if ((it.first - i) * i > it.second) {
                    winning.add(i)
                } else if (winning.isNotEmpty()) {
                    break
                }
            }
            winning.size
        }.reduce { acc, element -> acc * element }
    }
}
