package aoc2023

import util.io.println
import util.io.readExampleInputFile
import util.io.readInputFile

fun main() {
    val input = readInputFile(year = 2023, day = 7)
    Day07.task1(input).println()
    Day07.task2(input).println()

    // Examples
    val exampleInput = readExampleInputFile(year = 2023, day = 7)
    Day07.task1(exampleInput).println()
    Day07.task2(exampleInput).println()
}

object Day07 {

    fun task1(input: List<String>) = getBets(input, withJokers = false)

    fun task2(input: List<String>) = getBets(input, withJokers = true)

    private fun getBets(input: List<String>, withJokers: Boolean): Int {
        return input.map { handWithBet ->
            val (hand, bet) = handWithBet.split(" ")
            val cards = hand.toCharArray().map { it.toCardStrength(withJokers) }
            Hand(cards = cards, strength = cards.toHandStrength(withJokers), bet = bet.toInt())
        }.sorted().foldIndexed(0) { index, acc, hand ->
            acc + (index + 1) * hand.bet
        }
    }

    private fun Char.toCardStrength(withJokers: Boolean) = when(this) {
        'A' -> 14
        'K' -> 13
        'Q' -> 12
        'J' -> if (withJokers) JACK_VALUE_WITH_JOKERS else 11
        'T' -> 10
        else -> digitToInt()
    }

    private fun List<Int>.toHandStrength(withJokers: Boolean): Int {
        val sortedGroups = this.groupBy { it }.values.sortedByDescending { it.size }.toMutableList()
        if (sortedGroups.first().contains(JACK_VALUE_WITH_JOKERS)) sortedGroups.add(sortedGroups.removeFirst())
        var strength = sortedGroups.first().size * FACTOR_HIGHER_HAND_TYPE

        if (withJokers) {
            val numberOfJokers = sortedGroups.drop(1).fold(0) { acc, group ->
                acc + group.count { it == JACK_VALUE_WITH_JOKERS }
            }
            strength += numberOfJokers * FACTOR_HIGHER_HAND_TYPE
        }

        // Add additional strength for upgrades from one pair to two pairs as well as 3 of a kind to full house
        if (strength == 2 * FACTOR_HIGHER_HAND_TYPE || strength == 3 * FACTOR_HIGHER_HAND_TYPE) {
            if (sortedGroups.drop(1).filterNot { it.contains(JACK_VALUE_WITH_JOKERS) }.any { it.size == 2 }) {
                strength += ADDITION_UPGRADE_HAND_TYPE
            }
        }

        return strength
    }

    private data class Hand(val cards: List<Int>, val strength: Int, val bet: Int): Comparable<Hand> {
        override fun compareTo(other: Hand): Int {
            val difference = this.strength - other.strength
            if (difference == 0) {
                this.cards.zip(other.cards).forEach {
                    if (it.first != it.second) {
                        return it.first - it.second
                    }
                }
                error("Hands are exactly the same")
            } else {
                return difference
            }
        }
    }

    private const val JACK_VALUE_WITH_JOKERS = 1
    private const val FACTOR_HIGHER_HAND_TYPE = 2
    private const val ADDITION_UPGRADE_HAND_TYPE = 1
}
