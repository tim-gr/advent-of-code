package aoc2015

import java.io.File

fun Any.println() = println(this)

fun readInputFile(year: Int, day: Int): List<String> {
    val formattedDay = if (day < 10) "0$day" else day
    return File("src/main/resources/aoc$year/day$formattedDay.txt").readLines()
}
