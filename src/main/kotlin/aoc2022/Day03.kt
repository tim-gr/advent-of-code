package aoc2022

import java.io.File
import java.lang.Exception

fun main() {
    println(day03())
}

private const val FILE_PATH_DAY03 = "src/main/resources/aoc2022/day03.txt"

fun day03(filePath: String = FILE_PATH_DAY03): Pair<Int, Int> {
    val rucksacks = parseRucksacks(File(filePath).readLines())
    val solution1 = calculateMisplacedItemsPriorities(rucksacks)
    val solution2 = calculateBadgePriorities(rucksacks)
    return Pair(solution1, solution2)
}

internal fun calculateMisplacedItemsPriorities(rucksacks: List<Rucksack>): Int {
    return rucksacks.sumOf { it.findSameItemInCompartments().toPriority() }
}

internal fun calculateBadgePriorities(rucksacks: List<Rucksack>): Int {
    return rucksacks
        .chunked(size = 3)
        .sumOf { rucksackChunk ->
            rucksackChunk[0].getItems()
                .intersect(rucksackChunk[1].getItems())
                .intersect(rucksackChunk[2].getItems())
                .first()
                .toPriority()
        }
}

internal class Rucksack(items: String) {
    private val firstCompartment: Set<Char> = items.substring(0, items.length / 2).toSet()
    private val secondCompartment: Set<Char> = items.substring(items.length / 2, items.length).toSet()

    fun findSameItemInCompartments(): Char {
        return firstCompartment.intersect(secondCompartment).first()
    }

    fun getItems(): Set<Char> {
        return firstCompartment.plus(secondCompartment)
    }
}

internal fun parseRucksacks(lines: List<String>): List<Rucksack> {
    return lines.map { Rucksack(it) }
}

internal fun Char.toPriority(): Int {
    return if (!this.isLetter()) {
        throw Exception("Can only be called on letters.")
    } else if (this.isUpperCase()) {
        this.code - 38
    } else {
        this.code - 96
    }
}