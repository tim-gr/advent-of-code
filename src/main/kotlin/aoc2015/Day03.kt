package aoc2015

import util.io.println
import util.io.readInputFile

fun main() {
    val input = readInputFile(year = 2015, day = 3).first()
    Day03.task1(input).println()
    Day03.task2(input).println()
}

object Day03 {

    fun task1(input: String) = determineVisitedHouses(input).size

    fun task2(input: String): Int {
        val routeSanta = input.filterIndexed { index, _ -> index % 2 == 0 }
        val routeRobot = input.filterIndexed { index, _ -> index % 2 == 1 }
        return (determineVisitedHouses(routeSanta) + determineVisitedHouses(routeRobot)).size
    }

    private fun determineVisitedHouses(input: String): Set<Pair<Int, Int>> {
        var x = 0
        var y = 0
        val visitedHouses = mutableSetOf(x to y)
        input.forEach {
            when (it) {
                '^' -> y++
                'v' -> y--
                '>' -> x++
                '<' -> x--
                else -> error("Invalid input: $it")
            }
            visitedHouses.add(x to y)
        }
        return visitedHouses
    }
}
