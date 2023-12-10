package aoc2023

import util.coordinates.Coordinates
import util.io.println
import util.io.readExampleInputFile
import util.io.readInputFile

fun main() {
    val input = readInputFile(year = 2023, day = 10)
    Day10.task1(input).println()
    Day10.task2(input).println()

    // Examples
    Day10.task1(readExampleInputFile(year = 2023, day = 10, example = 1)).println()
    Day10.task2(readExampleInputFile(year = 2023, day = 10, example = 2)).println()
    Day10.task2(readExampleInputFile(year = 2023, day = 10, example = 3)).println()
    Day10.task2(readExampleInputFile(year = 2023, day = 10, example = 4)).println()
}

object Day10 {

    fun task1(input: List<String>) = getLoop(input.map { it.toCharArray() }.toTypedArray()).size / 2

    fun task2(input: List<String>): Int {
        val array = input.map { it.toCharArray() }.toTypedArray()
        val allPositions = getLoop(array)
        var containedTiles = 0
        for (y in array.indices) {
            for (x in array[0].indices) {
                if (!allPositions.contains(Coordinates(x, y))) {
                    if ((x downTo 0)
                            .count { array[y][it] in northPipes && allPositions.contains(Coordinates(it, y)) } % 2 != 0) {
                        containedTiles++
                    }
                }
            }
        }
        return containedTiles
    }

    private fun getLoop(array: Array<CharArray>): List<Coordinates> {
        val startPositionY = array.indexOfFirst { it.contains('S') }
        val startPositionX = array[startPositionY].indexOf('S')
        val startPosition = Coordinates(startPositionX, startPositionY)
        val startPositionConnectedPositions = startPosition.findConnectedPositions(array)
        array[startPositionY][startPositionX] = getActualStartPipe(startPosition, startPositionConnectedPositions)

        val allPositions = mutableListOf(startPosition)
        var lastPosition = startPosition
        var currentPosition = startPositionConnectedPositions[0]
        while (currentPosition != startPosition) {
            allPositions.add(currentPosition)
            currentPosition = currentPosition.findConnectedPositions(array).first { it != lastPosition }
                .also { lastPosition = currentPosition }
        }
        return allPositions
    }

    private fun getActualStartPipe(start: Coordinates, connected: List<Coordinates>) = when {
        connected[0].x == connected[1].x -> '-'
        connected[0].y == connected[1].y -> '|'
        connected[0].x > start.x && connected[1].y > start.y -> 'F'
        connected[0].x > start.x && connected[1].y > start.y -> 'L'
        connected[0].x < start.x && connected[1].y < start.y -> 'J'
        connected[0].x < start.x && connected[1].y > start.y -> '7'
        else -> error("Invalid connected positions")
    }

    private fun Coordinates.findConnectedPositions(array: Array<CharArray>): List<Coordinates> {
        val result = mutableListOf<Coordinates>()
        if (array[y][x] in westPipes && x != 0 && array[y][x - 1] in eastPipes)
            result.add(Coordinates(x - 1, y))
        if (array[y][x] in eastPipes && x != array[y].lastIndex && array[y][x + 1] in westPipes)
            result.add(Coordinates(x + 1, y))
        if (array[y][x] in northPipes && y != 0 && array[y - 1][x] in southPipes)
            result.add(Coordinates(x, y - 1))
        if (array[y][x] in southPipes && y != array.lastIndex && array[y + 1][x] in northPipes)
            result.add(Coordinates(x, y + 1))
        return result
    }

    private val northPipes = listOf('|', 'J', 'L', 'S')
    private val southPipes = listOf('|', '7', 'F', 'S')
    private val eastPipes = listOf('-', 'F', 'L', 'S')
    private val westPipes = listOf('-', '7', 'J', 'S')
}
