package util.io

import java.io.File

fun Any.println() = println(this)

fun readInputFile(year: Int, day: Int) = readFile(year, day)

fun readExampleInputFile(year: Int, day: Int, example: Int? = null) = readFile(
    year = year,
    day = day,
    suffix = example?.let { "-example-$it" } ?: "-example",
)

private fun readFile(year: Int, day: Int, suffix: String = ""): List<String> {
    val formattedDay = if (day < 10) "0$day" else day
    return File("src/main/resources/aoc$year/day$formattedDay$suffix.txt").readLines()
}
