package aoc2023

import util.io.println
import util.io.readExampleInputFile
import util.io.readInputFile

fun main() {
    val input = readInputFile(year = 2023, day = 5)
    Day05.task1(input).println()
    Day05.task2(input).println()

    // Examples
    val exampleInput = readExampleInputFile(year = 2023, day = 5)
    Day05.task1(exampleInput).println()
    Day05.task2(exampleInput).println()
}

object Day05 {

    fun task1(input: List<String>): Long {
        val seeds = input.first().substringAfter("seeds: ").split(" ").map { it.toLong() }
        val mappings = getMappings(input)
        return seeds.minOf { seed ->
            var currentTranslation = seed
            mappings.forEach { rangesList ->
                currentTranslation = rangesList.getTranslation(currentTranslation)
            }
            currentTranslation
        }
    }

    fun task2(input: List<String>): Long {
        val seedRanges = input.first().substringAfter("seeds: ").split(" ").map { it.toLong() }
            .chunked(2).map { Pair(it[0], it[1]) }
        val mappings = getMappings(input).reversed()
        var location = 0L
        while (true) {
            var currentTranslation = location
            mappings.forEach { rangesList ->
                currentTranslation = rangesList.getReversedTranslation(currentTranslation)
            }
            if (seedRanges.any { range -> currentTranslation >= range.first && currentTranslation < range.first + range.second }) {
                return location
            }
            location++
        }
    }

    private fun getMappings(input: List<String>): List<MutableList<Range>> {
        val mappings = mutableListOf<MutableList<Range>>()
        var currentMapNumber = -1
        input.drop(1).forEach { line ->
            if (line.isBlank()) {
                mappings.add(mutableListOf())
                currentMapNumber++
            } else if (!line.contains("map")) {
                mappings[currentMapNumber].add(line.toRange())
            }
        }
        return mappings
    }

    private fun String.toRange(): Range {
        val (destinationStart, sourceStart, length) = this.split(" ").map { it.toLong() }
        return Range(destinationStart, sourceStart, length)
    }

    private fun List<Range>.getTranslation(key: Long): Long {
        return this.find { key >= it.sourceStart && key < it.sourceStart + it.length }?.let {
            it.destinationStart + (key - it.sourceStart)
        } ?: key
    }

    private fun List<Range>.getReversedTranslation(key: Long): Long {
        return this.find { key >= it.destinationStart && key < it.destinationStart + it.length }?.let {
            it.sourceStart + (key - it.destinationStart)
        } ?: key
    }

    private data class Range(
        val destinationStart: Long,
        val sourceStart: Long,
        val length: Long,
    )
}
