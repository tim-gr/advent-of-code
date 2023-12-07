package aoc2023

import util.io.println
import util.io.readExampleInputFile
import util.io.readInputFile
import kotlin.math.pow

fun main() {
    val input = readInputFile(year = 2023, day = 4)
    Day04.task1(input).println()
    Day04.task2(input).println()

    // Examples
    val exampleInput = readExampleInputFile(year = 2023, day = 4)
    Day04.task1(exampleInput).println()
    Day04.task2(exampleInput).println()
}

object Day04 {

    fun task1(input: List<String>): Int {
        return input.sumOf { card ->
            val wins = getWins(card)
            if (wins > 0) 2.0.pow(wins - 1).toInt() else 0
        }
    }

    fun task2(input: List<String>): Int {
        val cards = IntArray(input.size) { 1 }
        input.forEachIndexed { index, card ->
            val wins = getWins(card)
            for (i in index + 1 .. index + wins) {
                repeat(cards[index]) { cards[i]++ }
            }
        }
        return cards.sum()
    }

    private fun getWins(card: String): Int {
        val (winningNumbers, cardNumbers) = card.substringAfter(": ").split(" | ")
            .map { numbers -> numbers.split(" ").filter { it.isNotEmpty() } }
        return cardNumbers.count { it in winningNumbers }
    }
}
