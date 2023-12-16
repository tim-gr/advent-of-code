package aoc2023

import util.grid.Coordinates
import util.grid.Direction
import util.grid.GridWalker
import util.io.println
import util.io.readExampleInputFile
import util.io.readInputFile

fun main() {

    val input = readInputFile(year = 2023, day = 16)
    Day16.task1(input).println()
    Day16.task2(input).println()

    // Examples
    val exampleInput = readExampleInputFile(year = 2023, day = 16)
    Day16.task1(exampleInput).println()
    Day16.task2(exampleInput).println()
}

object Day16 {

    fun task1(input: List<String>) = countEnergizedFields(input, Coordinates(0,0), Direction.EAST)

    fun task2(input: List<String>): Int {
        val entries = mutableListOf<Pair<Coordinates, Direction>>()
        for (y in input.indices) {
            entries.add(Coordinates(0, y) to Direction.EAST)
            entries.add(Coordinates(input[0].lastIndex, y) to Direction.WEST)
        }
        for (x in input[0].indices) {
            entries.add(Coordinates(x, 0) to Direction.SOUTH)
            entries.add(Coordinates(x, input.lastIndex) to Direction.NORTH)
        }
        return entries.maxOf { countEnergizedFields(input, it.first, it.second) }
    }

    private fun countEnergizedFields(input: List<String>, startPosition: Coordinates, startDirection: Direction): Int {
        val visited = mutableSetOf<Pair<Coordinates, Direction>>()
        walk(visited, startPosition, startDirection, input)
        return visited.distinctBy { it.first }.size
    }

    private fun walk(
        visited: MutableSet<Pair<Coordinates, Direction>>,
        startPosition: Coordinates,
        startDirection: Direction,
        input: List<String>
    ) {
        val gridWalker = GridWalker(input, startPosition, startDirection)
        do {
            visited.add(gridWalker.currentPosition to gridWalker.currentDirection)
            when (val newFieldValue = gridWalker.charOnCurrentPosition) {
                in listOf('/', '\\') -> { handleMirror(gridWalker, newFieldValue) }
                in listOf('|', '-') -> { handleSplitter(gridWalker, newFieldValue, visited, input) }
                else -> { gridWalker.moveIntoSameDirection() }
            }
        } while(gridWalker.currentPosition to gridWalker.currentDirection !in visited)
    }

    private fun handleMirror(gridWalker: GridWalker, newFieldValue: Char) {
        when (gridWalker.currentDirection) {
            Direction.NORTH -> if (newFieldValue == '/') gridWalker.moveEast() else gridWalker.moveWest()
            Direction.SOUTH -> if (newFieldValue == '/') gridWalker.moveWest() else gridWalker.moveEast()
            Direction.WEST -> if (newFieldValue == '/') gridWalker.moveSouth() else gridWalker.moveNorth()
            Direction.EAST -> if (newFieldValue == '/') gridWalker.moveNorth() else gridWalker.moveSouth()
        }
    }

    private fun handleSplitter(
        gridWalker: GridWalker,
        newFieldValue: Char,
        visited: MutableSet<Pair<Coordinates, Direction>>,
        input: List<String>,
    ) = when (gridWalker.currentDirection) {
        Direction.NORTH, Direction.SOUTH -> if (newFieldValue == '-') {
            walk(visited, gridWalker.currentPosition, Direction.WEST, input)
            gridWalker.moveEast()
        } else gridWalker.moveIntoSameDirection()
        Direction.WEST, Direction.EAST -> if (newFieldValue == '|') {
            walk(visited, gridWalker.currentPosition, Direction.NORTH, input)
            gridWalker.moveSouth()
        } else gridWalker.moveIntoSameDirection()
    }
}
