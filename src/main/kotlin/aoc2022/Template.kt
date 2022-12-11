package aoc2022

import java.io.File

fun main() {
    println(dayXX())
}

private const val FILE_PATH_DAYXX = "src/main/resources/aoc2022/dayXX.txt"

fun dayXX(filePath: String = FILE_PATH_DAYXX): Pair<Int, Int> {
    val lines = parseLines(File(filePath).readLines())
    val solution1 = solution1(lines)
    val solution2 = solution2(lines)
    return Pair(solution1, solution2)
}

internal fun parseLines(lines: List<String>): List<String> {
    return lines
}

internal fun solution1(lines: List<String>): Int {
    return 0
}

internal fun solution2(lines: List<String>): Int {
    return 0
}
