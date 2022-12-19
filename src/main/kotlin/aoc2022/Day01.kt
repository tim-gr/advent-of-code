package aoc2022

import java.io.File

fun main() {
    println(day01())
}

private const val FILE_PATH_DAY01 = "src/main/resources/aoc2022/day01.txt"
private const val TOP_THREE_ELVES = 3

fun day01(filePath: String = FILE_PATH_DAY01): Pair<Int, Int> {
    val lines = File(filePath).readLines()
    val solution1 = determineMostCalories(lines)
    val solution2 = determineTopThreeElvesCalories(lines)
    return Pair(solution1, solution2)
}

internal fun determineMostCalories(lines: List<String>): Int {
    return getCaloriesList(lines).max()
}

internal fun determineTopThreeElvesCalories(lines: List<String>): Int {
    return getCaloriesList(lines).sortedDescending().subList(0, TOP_THREE_ELVES).sum()
}

internal fun getCaloriesList(lines: List<String>): List<Int> {
    val caloriesSums = mutableListOf<Int>()
    var currentCaloriesSum = 0

    lines.forEachIndexed { index, line ->
        if (line.isBlank()) {
            caloriesSums.add(currentCaloriesSum)
            currentCaloriesSum = 0
        } else if (line.isNotBlank() || index == lines.lastIndex) {
            currentCaloriesSum += line.toInt()
        }
    }
    caloriesSums.add(currentCaloriesSum)
    return caloriesSums
}
