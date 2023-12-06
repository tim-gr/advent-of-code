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
    Day04.task2(listOf(
        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11",
    )).println()
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
