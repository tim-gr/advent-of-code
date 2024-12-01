package aoc2024

import util.io.println
import util.io.readExampleInputFile
import util.io.readInputFile
import kotlin.math.abs

fun main() {
    val input = readInputFile(year = 2024, day = 1)
    Day01.task1(input).println()
    Day01.task2(input).println()

    // Examples
    Day01.task1(readExampleInputFile(year = 2024, day = 1, example = 1)).println()
    Day01.task2(readExampleInputFile(year = 2024, day = 1, example = 1)).println()
}

object Day01 {

    fun task1(input: List<String>): Int {
        val (list1, list2) = parseLists(input)
        return list1.sorted().zip(list2.sorted()).map { abs(it.second - it.first) }.sum()
    }

    fun task2(input: List<String>): Int {
        val (list1, list2) = parseLists(input)
        val occurrencesInList2 = list2.groupBy { it }.mapValues { it.value.count() }
        return list1.sumBy { it * occurrencesInList2.getOrDefault(key = it, defaultValue = 0) }
    }

    private fun parseLists(input: List<String>): Pair<List<Int>, List<Int>> {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()
        input.forEach {
            val (first, second) = it.split("   ")
            list1.add(first.toInt())
            list2.add(second.toInt())
        }
        return Pair(list1, list2)
    }
}
