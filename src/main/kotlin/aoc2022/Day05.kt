package aoc2022

import java.io.File

fun main() {
    println(day05())
}

private const val FILE_PATH_DAY05 = "src/main/resources/aoc2022/day05.txt"

fun day05(filePath: String = FILE_PATH_DAY05): Pair<String, String> {
    val lines = File(filePath).readLines()
    val moves = parseMoves(lines)
    var stacks = parseStacks(lines)
    val solution1 = calculateTopElementsOnStacks(stacks, moves, false)
    stacks = parseStacks(lines)
    val solution2 = calculateTopElementsOnStacks(stacks, moves, true)
    return Pair(solution1, solution2)
}

internal fun calculateTopElementsOnStacks(
    stacks: Map<Int, MutableList<Char>>,
    moves: List<Move>,
    multipleItemsAtOnce: Boolean
): String {

    if (multipleItemsAtOnce) {
        moves.forEach { move ->
            val fromStack = stacks[move.from]!!
            val movedElements = fromStack.takeLast(move.amount)
            repeat(move.amount) { fromStack.removeAt(fromStack.lastIndex) }
            stacks[move.to]!!.addAll(movedElements)
        }
    } else {
        moves.forEach { move ->
            repeat(move.amount) {
                stacks[move.to]!!.add(stacks[move.from]!!.removeLast())
            }
        }
    }

    return stacks.values.fold("") { acc, element ->
        acc + element[element.lastIndex]
    }
}

internal data class Move(val amount: Int, val from: Int, val to: Int)

internal fun parseStacks(lines: List<String>): Map<Int, MutableList<Char>> {
    val stacks = mutableMapOf<Int, MutableList<Char>>()
    lines
        .takeWhile { it.contains("1").not() }
        .map { it.chunked(4) }
        .forEach {
            it.forEachIndexed { index, element ->
                if (stacks[index + 1] == null) {
                    stacks[index + 1] = mutableListOf()
                }
                if (element.isNotBlank()) {
                    stacks[index + 1]!!.add(0, element[1])
                }
            }
        }
    return stacks
}

internal fun parseMoves(lines: List<String>): List<Move> {
    return lines
        .takeLastWhile { it.startsWith("move") }
        .map { it.split(" ") }
        .map { Move(it[1].toInt(), it[3].toInt(), it[5].toInt()) }
}