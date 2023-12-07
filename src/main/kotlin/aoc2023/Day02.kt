package aoc2023

import util.io.println
import util.io.readExampleInputFile
import util.io.readInputFile

fun main() {
    val input = readInputFile(year = 2023, day = 2)
    Day02.task1(input).println()
    Day02.task2(input).println()

    // Examples
    val exampleInput = readExampleInputFile(year = 2023, day = 2)
    Day02.task1(exampleInput).println()
    Day02.task2(exampleInput).println()
}

object Day02 {

    fun task1(input: List<String>): Int {
        val games = parseGames(input)
        return games.sumOf { game ->
            val maxBlue = game.draws.maxOf { it.blue }
            val maxGreen = game.draws.maxOf { it.green }
            val maxRed = game.draws.maxOf { it.red }
            if (maxBlue > 14 || maxGreen > 13 || maxRed > 12) 0 else game.id
        }
    }

    fun task2(input: List<String>): Int {
        val games = parseGames(input)
        return games.sumOf { game ->
            val maxBlue = game.draws.maxOf { it.blue }
            val maxGreen = game.draws.maxOf { it.green }
            val maxRed = game.draws.maxOf { it.red }
            maxBlue * maxGreen * maxRed
        }
    }

    private fun parseGames(input: List<String>): List<Game> {
        return input.mapIndexed { index, game ->
            val draws = game.substringAfter(": ").split("; ").map { round ->
                round.split(", ").associate { draw ->
                    val (number, color) = draw.split(" ")
                    color to number.toInt()
                }.let { drawsList ->
                    Draw(blue = drawsList["blue"] ?: 0, green = drawsList["green"] ?: 0, red = drawsList["red"] ?: 0)
                }
            }
            Game(id = index + 1, draws = draws)
        }
    }
}

private data class Game(
    val id: Int,
    val draws: List<Draw>
)

private data class Draw(
    val blue: Int,
    val green: Int,
    val red: Int
)
