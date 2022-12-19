package aoc2022

import util.coordinates.drawCoordinates
import java.io.File

fun main() {
    println(day14())
}

private const val FILE_PATH_DAY14 = "src/main/resources/aoc2022/day14.txt"
private const val SAND_SOURCE_X = 500
private const val SAND_SOURCE_Y = 0

fun day14(filePath: String = FILE_PATH_DAY14): Pair<Int, Int> {
    val lines = File(filePath).readLines()
    val mineArray = parseRocks(lines, false)
    val solution1 = simulateSand(mineArray)
    val mineArrayWithFloor = parseRocks(lines, true)
    val solution2 = simulateSand(mineArrayWithFloor)
    return Pair(solution1, solution2)
}

fun simulateSand(mineArray: Array<Array<CaveObject>>): Int {
    var counter = 0
    var currentSand = CavePosition(SAND_SOURCE_X, SAND_SOURCE_Y)
    mineArray[currentSand.x][currentSand.y] = CaveObject.SAND
    val outside = mineArray[0].lastIndex
    while (currentSand.y != outside) {
        if (mineArray[currentSand.x][currentSand.y + 1] == CaveObject.AIR) {
            mineArray[currentSand.x][currentSand.y] = CaveObject.AIR
            currentSand.y++
        } else if (mineArray[currentSand.x - 1][currentSand.y + 1] == CaveObject.AIR) {
            mineArray[currentSand.x][currentSand.y] = CaveObject.AIR
            currentSand.x--
            currentSand.y++
        } else if (mineArray[currentSand.x + 1][currentSand.y + 1] == CaveObject.AIR) {
            mineArray[currentSand.x][currentSand.y] = CaveObject.AIR
            currentSand.x++
            currentSand.y++
        } else {
            counter++
            if (mineArray[SAND_SOURCE_X][SAND_SOURCE_Y] == CaveObject.SAND) {
                break
            }
            currentSand = CavePosition(SAND_SOURCE_X, SAND_SOURCE_Y)
        }
        mineArray[currentSand.x][currentSand.y] = CaveObject.SAND
    }

    drawCoordinates(mineArray) { it.charRepresentation }

    return counter
}

internal fun parseRocks(lines: List<String>, withFloor: Boolean): Array<Array<CaveObject>> {
    var allRockPositions = parseRockPositions(lines)

    var maxX = allRockPositions.maxOf { it.x }
    var maxY = allRockPositions.maxOf { it.y }

    if (withFloor) {
        allRockPositions = allRockPositions.toMutableSet()
        val necessaryWidthFloor = SAND_SOURCE_X + maxY + 2
        for (x in 0..necessaryWidthFloor) {
            allRockPositions.add(CavePosition(x, maxY + 2))
        }
        maxX = necessaryWidthFloor
        maxY += 2
    }

    val array = Array(maxX + 2) { Array(maxY + 1) { CaveObject.AIR } }
    allRockPositions.forEach {
        array[it.x][it.y] = CaveObject.ROCK
    }

    return array
}

fun parseRockPositions(lines: List<String>): Set<CavePosition> {
    val allRockPositions = mutableSetOf<CavePosition>()
    lines.forEach { line ->
        val rockPositions = line
            .split(" -> ")
            .map { CavePosition(it.substringBefore(',').toInt(), it.substringAfter(',').toInt()) }

        for (i in 0..rockPositions.lastIndex) {
            if (i != rockPositions.lastIndex) {
                val current = rockPositions[i]
                val next = rockPositions[i + 1]
                for (x in minOf(current.x, next.x)..maxOf(current.x, next.x)) {
                    for (y in minOf(current.y, next.y)..maxOf(current.y, next.y)) {
                        allRockPositions.add(CavePosition(x, y))
                    }
                }
            }
        }
    }
    return allRockPositions
}

data class CavePosition(var x: Int, var y: Int) {
    override fun equals(other: Any?): Boolean {
        return other is CavePosition && x == other.x && y == other.y
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}
enum class CaveObject(val charRepresentation: Char) {
    ROCK('#'), SAND('o'), AIR('.')
}
