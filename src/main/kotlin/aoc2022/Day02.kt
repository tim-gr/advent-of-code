package aoc2022

import java.io.File

fun main() {
    println(day02())
}

private const val FILE_PATH_DAY02 = "src/main/resources/aoc2022/day02.txt"
private const val POINTS_WIN_GAME = 6
private const val POINTS_TIED_GAME = 3
private const val POINTS_LOST_GAME = 0
private const val POINTS_ROCK = 1
private const val POINTS_PAPER = 2
private const val POINTS_SCISSOR = 3

fun day02(filePath: String = FILE_PATH_DAY02): Pair<Int, Int> {
    val lines = File(filePath).readLines()
    val solution1 = determineScoreSolution1(lines)
    val solution2 = determineScoreSolution2(lines)
    return Pair(solution1, solution2)
}

internal fun determineScoreSolution1(lines: List<String>): Int {
    return lines
        .map { it.split(" ") }
        .map { Pair(it[0].convertToSymbol(), it[1].convertToSymbol()) }
        .fold(0) { acc, element ->
            acc + element.second.score + determineGameResult(element.first, element.second).score
        }
}

internal fun determineScoreSolution2(lines: List<String>): Int {
    return lines
        .map { it.split(" ") }
        .map { Pair(it[0].convertToSymbol(), it[1].convertToSymbol(it[0].convertToSymbol())) }
        .fold(0) { acc, element ->
            acc + element.second.score + determineGameResult(element.first, element.second).score
        }
}

internal fun determineGameResult(opponentSymbol: Symbol, ownSymbol: Symbol): GameResult {
    return when (opponentSymbol) {
        ownSymbol -> GameResult.TIE
        ownSymbol.winsAgainst() -> GameResult.WIN
        else -> GameResult.LOSS
    }
}

internal enum class GameResult(val score: Int) {
    WIN(POINTS_WIN_GAME), TIE(POINTS_TIED_GAME), LOSS(POINTS_LOST_GAME)
}

internal enum class Symbol(val score: Int) {
    ROCK(POINTS_ROCK), PAPER(POINTS_PAPER), SCISSOR(POINTS_SCISSOR)
}

internal fun String.convertToSymbol(): Symbol {
    return when (this) {
        "A", "X" -> Symbol.ROCK
        "B", "Y" -> Symbol.PAPER
        "C", "Z" -> Symbol.SCISSOR
        else -> throw IllegalArgumentException("No valid Rock-Paper-Scissor symbol: $this")
    }
}

internal fun String.convertToSymbol(opponentSymbol: Symbol): Symbol {
    return when (this) {
        "X" -> opponentSymbol.winsAgainst()
        "Y" -> opponentSymbol
        "Z" -> opponentSymbol.losesAgainst()
        else -> throw IllegalArgumentException("No valid Rock-Paper-Scissor symbol: $this")
    }
}

internal fun Symbol.winsAgainst(): Symbol {
    return when (this) {
        Symbol.ROCK -> Symbol.SCISSOR
        Symbol.PAPER -> Symbol.ROCK
        Symbol.SCISSOR -> Symbol.PAPER
    }
}

internal fun Symbol.losesAgainst(): Symbol {
    return when (this) {
        Symbol.ROCK -> Symbol.PAPER
        Symbol.PAPER -> Symbol.SCISSOR
        Symbol.SCISSOR -> Symbol.ROCK
    }
}
