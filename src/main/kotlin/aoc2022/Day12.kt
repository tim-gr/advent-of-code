package aoc2022

import java.io.File

fun main() {
    println(day12())
}

private const val FILE_PATH_DAY12 = "src/main/resources/aoc2022/day12.txt"

fun day12(filePath: String = FILE_PATH_DAY12): Pair<Int, Int> {
    val lines = File(filePath).readLines()
    val map = parseMap(lines)
    val solution1 = (
        findShortestPathLength(map, getStartPosition(map), getTargetPosition(map))
            as FindShortestPathLengthSuccessful
        ).length
    val solution2 = getLowestPositions(map)
        .map { findShortestPathLength(map, it, getTargetPosition(map)) }
        .filterIsInstance<FindShortestPathLengthSuccessful>()
        .minOf { it.length }
    return Pair(solution1, solution2)
}

internal fun findShortestPathLength(
    map: Array<CharArray>,
    startPosition: MapPosition,
    targetPosition: MapPosition,
): FindShortestPathLengthResult {
    val queue = ArrayDeque<MapPosition>()
    val visitedPositions = mutableSetOf<MapPosition>()

    queue.add(startPosition)
    visitedPositions.add(startPosition)

    while (queue.isNotEmpty()) {
        val currentPosition = queue.removeFirst()
        if (currentPosition == targetPosition) {
            return FindShortestPathLengthSuccessful(currentPosition.stepNumber)
        }
        val nextPositions = findNextMapPositions(map, currentPosition)
        nextPositions.forEach { nextPosition ->
            if (visitedPositions.contains(nextPosition).not()) {
                visitedPositions.add(nextPosition)
                nextPosition.stepNumber = currentPosition.stepNumber + 1
                queue.add(nextPosition)
            }
        }
    }

    return FindShortestPathLengthFailure()
}

internal fun findNextMapPositions(
    map: Array<CharArray>,
    currentPosition: MapPosition
): List<MapPosition> {
    return listOf(
        MapPosition(currentPosition.x + 1, currentPosition.y),
        MapPosition(currentPosition.x - 1, currentPosition.y),
        MapPosition(currentPosition.x, currentPosition.y + 1),
        MapPosition(currentPosition.x, currentPosition.y - 1)
    ).filter {
        map[0].indices.contains(it.x) && map.indices.contains(it.y)
    }.filter {
        val heightDifference = map[it.y][it.x].code - map[currentPosition.y][currentPosition.x].code
        heightDifference <= 1 ||
            (map[currentPosition.y][currentPosition.x] == 'S' && map[it.y][it.x] == 'a') ||
            (map[currentPosition.y][currentPosition.x] == 'a' && map[it.y][it.x] == 'S') ||
            (map[currentPosition.y][currentPosition.x] == 'z' && map[it.y][it.x] == 'E')
    }
}

internal data class MapPosition(val x: Int, val y: Int, var stepNumber: Int = 0) {
    override fun equals(other: Any?): Boolean {
        return (other is MapPosition) && (x == other.x) && (y == other.y)
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + stepNumber
        return result
    }
}

internal interface FindShortestPathLengthResult
internal class FindShortestPathLengthSuccessful(val length: Int) : FindShortestPathLengthResult
internal class FindShortestPathLengthFailure : FindShortestPathLengthResult

internal fun parseMap(lines: List<String>): Array<CharArray> {
    return lines.fold(listOf<CharArray>()) { acc, line ->
        acc + line.toCharArray()
    }.toTypedArray()
}

internal fun getStartPosition(map: Array<CharArray>): MapPosition = findPositionWithCharacter(map, 'S')[0]

internal fun getTargetPosition(map: Array<CharArray>): MapPosition = findPositionWithCharacter(map, 'E')[0]

internal fun getLowestPositions(map: Array<CharArray>): List<MapPosition> {
    return findPositionWithCharacter(map, 'a') + getStartPosition(map)
}

private fun findPositionWithCharacter(map: Array<CharArray>, character: Char): List<MapPosition> {
    val positions = mutableListOf<MapPosition>()
    map.forEachIndexed { y, rows ->
        rows.forEachIndexed { x, letter ->
            if (letter == character) {
                positions.add(MapPosition(x, y))
            }
        }
    }
    return positions
}
