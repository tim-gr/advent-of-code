package aoc2023

import util.io.println
import util.io.readExampleInputFile
import util.io.readInputFile
import util.string.splitAndKeepDelimiters

fun main() {

    val input = readInputFile(year = 2023, day = 14)
    Day14.task1(input).println()
    Day14.task2(input).println()

    // Examples
    val exampleInput = readExampleInputFile(year = 2023, day = 14)
    Day14.task1(exampleInput).println()
    Day14.task2(exampleInput).println()
}

object Day14 {

    private const val CYCLES_TASK_2 = 1000000000

    fun task1(input: List<String>): Int {
        val columns = mutableListOf<String>()
        for (x in input[0].indices) {
            columns.add(input.fold("") { acc, row -> acc + row[x] })
        }
        return columns.sumOf { column ->
            val groups = column.splitAndKeepDelimiters('#')
            var counter = column.length
            groups.sumOf { group ->
                val movingRocks = group.count { it == 'O' }
                val result = movingRocks * counter - (movingRocks - 1 downTo 1).sum()
                counter -= group.length
                result
            }
        }
    }

    fun task2(input: List<String>): Int {
        val array = input.map { it.toCharArray() }.toTypedArray()
        val cache = hashMapOf<String, Int>()
        var cycleCounter = 0
        cache[array.joinToString("") { rows -> rows.joinToString("") }] = 0
        while (cycleCounter < CYCLES_TASK_2) {
            tiltNW(array, isColumns = true)
            tiltNW(array, isColumns = false)
            tiltSE(array, isColumns = true)
            tiltSE(array, isColumns = false)
            val arrayInCacheFormat = array.joinToString("") { rows -> rows.joinToString("") }
            if (cache[arrayInCacheFormat] == null) {
                cycleCounter++
                cache[arrayInCacheFormat] = cycleCounter
            } else {
                val indexFirstLoopItem = cache[arrayInCacheFormat]!!
                val loopLength = cycleCounter + 1 - indexFirstLoopItem
                val remaining = (CYCLES_TASK_2 - indexFirstLoopItem) % loopLength
                repeat(remaining) {
                    tiltNW(array, isColumns = true)
                    tiltNW(array, isColumns = false)
                    tiltSE(array, isColumns = true)
                    tiltSE(array, isColumns = false)
                }
                break
            }
        }

        var sum = 0
        for (y in array.indices) {
            array[y].forEach { if (it == 'O') sum += (array.size - y) }
        }
        return sum
    }

    private fun tiltNW(array: Array<CharArray>, isColumns: Boolean) {
        val lines = if (isColumns) {
            val columns = mutableListOf<String>()
            for (x in array[0].indices) {
                columns.add(array.fold("") { acc, row -> acc + row[x] })
            }
            columns
        } else array.map { it.joinToString("") }

        return lines.withIndex().forEach { column ->
            val groups = column.value.splitAndKeepDelimiters('#')
            var counter = 0
            groups.forEach { group ->
                if (group.first() != '#') {
                    val movingRocks = group.count { it == 'O' }
                    for (y in counter until counter + movingRocks) {
                        if (isColumns) {
                            array[y][column.index] = 'O'
                        } else {
                            array[column.index][y] = 'O'
                        }
                    }
                    for (y in counter + movingRocks until counter + group.length) {
                        if (isColumns) {
                            array[y][column.index] = '.'
                        } else {
                            array[column.index][y] = '.'
                        }
                    }
                }
                counter += group.length
            }
        }
    }

    private fun tiltSE(array: Array<CharArray>, isColumns: Boolean) {
        val lines = if (isColumns) {
            val columns = mutableListOf<String>()
            for (x in array[0].indices) {
                columns.add(array.fold("") { acc, row -> acc + row[x] })
            }
            columns
        } else array.map { it.joinToString("") }

        return lines.withIndex().forEach { column ->
            val groups = column.value.splitAndKeepDelimiters('#')
            var counter = column.value.lastIndex
            groups.reversed().forEach { group ->
                if (group.first() != '#') {
                    val movingRocks = group.count { it == 'O' }
                    for (y in counter downTo counter - movingRocks + 1) {
                        if (isColumns) {
                            array[y][column.index] = 'O'
                        } else {
                            array[column.index][y] = 'O'
                        }
                    }
                    for (y in counter - movingRocks  downTo  counter - group.length + 1) {
                        if (isColumns) {
                            array[y][column.index] = '.'
                        } else {
                            array[column.index][y] = '.'
                        }
                    }
                }
                counter -= group.length
            }
        }
    }
}
