package aoc2023

import util.coordinates.Coordinates
import util.io.println
import util.io.readInputFile

fun main() {
    val input = readInputFile(year = 2023, day = 3)
    Day03.task1(input).println()
    Day03.task2(input).println()

    // Examples
    val exampleInput = listOf(
        "467..114..",
        "...*......",
        "..35..633.",
        "......#...",
        "617*......",
        ".....+.58.",
        "..592.....",
        "......755.",
        "...\$.*....",
        ".664.598..",
    )
    Day03.task1(exampleInput).println()
    Day03.task2(exampleInput).println()
}

object Day03 {

    fun task1(input: List<String>): Int {
        val array = input.map { it.toCharArray() }.toTypedArray()
        val numbersWithAdjacentFields = getNumbersWithAdjacentFields(array)
        return numbersWithAdjacentFields.entries
            .filter { numberWithFields ->
                numberWithFields.value.any { array[it.y][it.x] != '.' && !array[it.y][it.x].isDigit() }
            }.sumOf { it.key.number.toInt() }
    }

    fun task2(input: List<String>): Int {
        val array = input.map { it.toCharArray() }.toTypedArray()
        val numbersWithAdjacentFields = getNumbersWithAdjacentFields(array)

        val symbolPositionsWithAdjacentNumbers = mutableMapOf<Coordinates, MutableList<Int>>()
        numbersWithAdjacentFields.entries.forEach { numberWithFields ->
            numberWithFields.value.filter { array[it.y][it.x] == '*' }.forEach {
                symbolPositionsWithAdjacentNumbers.putIfAbsent(it, mutableListOf())
                symbolPositionsWithAdjacentNumbers[it]?.add(numberWithFields.key.number.toInt())
            }
        }

        return symbolPositionsWithAdjacentNumbers.values.filter { it.size == 2 }.sumOf { it[0] * it[1] }
    }

    private fun getNumbersWithAdjacentFields(array: Array<CharArray>): Map<NumberWithPosition, List<Coordinates>> {
        val numbersWithPosition = findNumbersWithPosition(array)
        return numbersWithPosition.associateWith { numberWithPosition ->
            numberWithPosition.toAdjacentFields().filter {
                it.x >= 0 && it.y >= 0 && it.x < array.first().size && it.y < array.size
            }
        }
    }

    private fun findNumbersWithPosition(array: Array<CharArray>): List<NumberWithPosition> {
        val numbersWithPosition = mutableListOf<NumberWithPosition>()
        for (row in array.indices) {
            var numberWithPosition = NumberWithPosition()
            for (column in 0 until array.first().size) {
                val character = array[row][column]
                if (character.isDigit()) {
                    if (numberWithPosition.start == null) {
                        numberWithPosition.start = Coordinates(column, row)
                    }
                    numberWithPosition.end = Coordinates(column, row)
                    numberWithPosition.number += character
                } else {
                    if (numberWithPosition.number.isNotBlank()) {
                        numbersWithPosition.add(numberWithPosition)
                        numberWithPosition = NumberWithPosition()
                    }
                }
            }
            if (numberWithPosition.number.isNotBlank()) {
                numbersWithPosition.add(numberWithPosition)
            }
        }
        return numbersWithPosition
    }

    private fun NumberWithPosition.toAdjacentFields(): List<Coordinates> {
        require(start != null && end != null)
        val xRange = Pair(start!!.x - 1, end!!.x + 1)
        val adjacentFields = mutableListOf<Coordinates>()
        for (x in xRange.first..xRange.second) {
            adjacentFields.add(Coordinates(x, start!!.y - 1))
            adjacentFields.add(Coordinates(x, start!!.y + 1))
        }
        adjacentFields.add(Coordinates(xRange.first, start!!.y))
        adjacentFields.add(Coordinates(xRange.second, start!!.y))
        return adjacentFields
    }

    private data class NumberWithPosition(
        var number: String = "",
        var start: Coordinates? = null,
        var end: Coordinates? = null,
    )
}
