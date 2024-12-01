package aoc2023

import util.grid.Coordinates
import util.io.println
import util.io.readExampleInputFile
import util.io.readInputFile

fun main() {

    val input = readInputFile(year = 2023, day = 17)
    //Day17.task1(input).println()
    Day17.task2(input).println()

    // Examples
    val exampleInput = readExampleInputFile(year = 2023, day = 17)
    Day17.task1(exampleInput).println()
    Day17.task2(exampleInput).println()
}

object Day17 {

    fun task1(input: List<String>): Int {
        val array = input.mapIndexed { y, line ->
            line.toCharArray().mapIndexed { x, column -> Position(Coordinates(x, y), column.digitToInt(), 0) }
        }.toTypedArray()
        val visited = mutableSetOf<Coordinates>()
        val goal = Coordinates(array[0].lastIndex, array.lastIndex)
        array[0][0].distance = array[0][0].value
        val orderedNextPositions = mutableListOf(array[0][0])
        while (orderedNextPositions.last().loc != goal) {
            val currentlyShortest = orderedNextPositions.removeLast()
            visited.add(currentlyShortest.loc)
            val newNextPositions = getNextPositions(array, currentlyShortest).filterNot { it.loc in visited }
            newNextPositions.forEach {
                val newDistance = currentlyShortest.distance + it.value
                if (it.distance == 0 || newDistance < it.distance) {
                    it.distance = newDistance
                    orderedNextPositions.add(it)
                }
            }
            orderedNextPositions.sortByDescending { it.distance }
        }
        return array[goal.y][goal.x].distance
    }

    fun task2(input: List<String>): Int {
        return 0
    }

    private fun getNextPositions(array: Array<List<Position>>, current: Position): List<Position> {
        val last3 = array[current.loc.y][current.loc.x].last.takeLast(3)
        return listOf(
            Coordinates(current.loc.x - 1, current.loc.y),
            Coordinates(current.loc.x + 1, current.loc.y),
            Coordinates(current.loc.x, current.loc.y - 1),
            Coordinates(current.loc.x, current.loc.y + 1),
        ).filterNot {
            it.y < 0 || it.y > array.lastIndex || it.x < 0 || it.x > array[0].lastIndex
        }.filter { position ->
            if (last3.size == 3 && last3.all { it == last3[0]}) {
                (position.y - current.loc.y to position.x - current.loc.x) != last3[0]
            } else true
        }.map {
            array[it.y][it.x].also { position ->
                position.last.addAll(current.last)
                position.last.add(it.y - current.loc.y to it.x - current.loc.x)
            }
        }
    }

    private data class Position(val loc: Coordinates, val value: Int, var distance: Int = 0, var last: MutableList<Pair<Int, Int>> = mutableListOf())
}
