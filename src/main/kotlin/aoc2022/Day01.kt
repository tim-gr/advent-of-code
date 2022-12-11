package aoc2022

import java.io.File

fun main() {
    println(day01())
}

private const val FILE_PATH_DAY01 = "src/main/resources/aoc2022/day01.txt"

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
    return getCaloriesList(lines).sortedDescending().subList(0,3).sum()
}

internal fun getCaloriesList(lines: List<String>): List<Int> {
    val caloriesList = mutableListOf<Int>()
    var currentCaloriesSum = 0
    lines.forEachIndexed { index, line ->
        if (line.isBlank()) {
            caloriesList.add(currentCaloriesSum)
            currentCaloriesSum = 0
        } else if (line.isNotBlank() || index == lines.lastIndex) {
            currentCaloriesSum += line.toInt()
        }
    }
    caloriesList.add(currentCaloriesSum)
    return caloriesList
}
