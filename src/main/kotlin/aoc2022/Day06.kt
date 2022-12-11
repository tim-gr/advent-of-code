package aoc2022

import java.io.File

fun main() {
    println(day06())
}

private const val FILE_PATH_DAY06 = "src/main/resources/aoc2022/day06.txt"

fun day06(filePath: String = FILE_PATH_DAY06): Pair<Int, Int> {
    val line = File(filePath).readLines().first()
    val solution1 = calculateToken(line, 4)
    val solution2 = calculateToken(line, 14)
    return Pair(solution1, solution2)
}

internal fun calculateToken(line: String, distinctMarkers: Int): Int {
    val array = ArrayDeque<Char>()
    line.forEachIndexed { index, element ->
        array.addLast(element)
        if (array.size == distinctMarkers + 1) {
            array.removeFirst()
            if (array.toSet().size == distinctMarkers) {
                return index + 1
            }
        }
    }
    throw Exception("No token with number of <distinctMarkers> in <line>.")
}
