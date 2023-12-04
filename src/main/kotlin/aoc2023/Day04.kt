package aoc2023

import util.io.println
import util.io.readInputFile
import kotlin.math.pow

fun main() {
    val input = readInputFile(year = 2023, day = 4)
    Day04.task1(input).println()
    Day04.task2(input).println()

    // Examples
    Day04.task1(listOf("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53")).println()
    Day04.task2(listOf("")).println()
}

object Day04 {

    fun task1(input: List<String>): Int {
        return input.sumOf { card ->
            val winningNumbers = card.substringAfter(":").substringBefore("|")
                .split(" ").filter { it.isNotEmpty() }.map { it.trim() }
            val cardNumbers = card.substringAfter("|").split(" ").filter { it.isNotEmpty() }.map { it.trim() }
            val wins = cardNumbers.count { it in winningNumbers }
            if (wins > 0) 2.0.pow(wins - 1).toInt() else 0
        }
    }

    fun task2(input: List<String>): Int {
        return 0
    }
}
