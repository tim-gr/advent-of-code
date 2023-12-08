package aoc2023

import util.io.println
import util.io.readExampleInputFile
import util.io.readInputFile
import util.math.lcm

fun main() {
    val input = readInputFile(year = 2023, day = 8)
    Day08.task1(input).println()
    Day08.task2(input).println()

    // Examples
    Day08.task1(readExampleInputFile(year = 2023, day = 8, example = 1)).println()
    Day08.task1(readExampleInputFile(year = 2023, day = 8, example = 2)).println()
    Day08.task2(readExampleInputFile(year = 2023, day = 8, example = 3)).println()
}

object Day08 {

    fun task1(input: List<String>): Int {
        return getStepsToEnd(start = "AAA", endCondition = { it == "ZZZ" }, parseDirections(input), parseMapping(input))
    }

    fun task2(input: List<String>): Long {
        val directions = parseDirections(input)
        val mapping = parseMapping(input)
        val startLocations = mapping.keys.filter { it.endsWith("A") }
        return startLocations.map { location ->
            getStepsToEnd(start = location, endCondition = { it.endsWith("Z") }, directions, mapping)
        }.map { it.toLong() }.reduce { acc, requiredSteps -> lcm(acc, requiredSteps) }
    }

    private fun parseDirections(input: List<String>) = input.first()

    private fun parseMapping(input: List<String>) = input.drop(2).associate {
        val (source, leftDestination, rightDestination) = it.split(" = (", ", ", ")")
        Pair(source, Pair(leftDestination, rightDestination))
    }

    private fun getStepsToEnd(
        start: String,
        endCondition: (String) -> Boolean,
        directions: String,
        mapping: Map<String, Pair<String, String>>,
    ): Int {
        var currentLocation = start
        var counter = 0
        while (!endCondition(currentLocation)) {
            currentLocation = if (directions[(counter % directions.length)] == 'L') {
                mapping[currentLocation]!!.first
            } else mapping[currentLocation]!!.second
            counter++
        }
        return counter
    }
}
