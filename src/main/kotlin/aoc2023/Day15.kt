package aoc2023

import util.io.println
import util.io.readExampleInputFile
import util.io.readInputFile

fun main() {

    val input = readInputFile(year = 2023, day = 15)
    Day15.task1(input).println()
    Day15.task2(input).println()

    // Examples
    val exampleInput = readExampleInputFile(year = 2023, day = 15)
    Day15.task1(exampleInput).println()
    Day15.task2(exampleInput).println()
}

object Day15 {

    fun task1(input: List<String>) = input.first().split(",").sumOf { string -> getHash(string) }

    fun task2(input: List<String>): Int {
        val boxes = mutableMapOf<Int, MutableList<Lens>>()
        input.first().split(",").forEach { string ->
            val label = string.split("-", "=")[0]
            val focalLength = string.last()
            val lens = Lens(label, if (focalLength.isDigit()) focalLength.digitToInt() else 0)
            val box = getHash(label)

            if (string.contains("-")) {
                boxes[box]?.removeIf { it.label == label }
            } else {
                if (boxes[box] == null) boxes[box] = mutableListOf(lens)
                boxes[box]!!.apply {
                    val index = indexOfFirst { it.label == label }
                    if (index != -1) {
                        removeIf { it.label == label }
                        add(index, lens)
                    } else {
                        add(lens)
                    }
                }
            }
        }

        return boxes.entries.sumOf {
            it.value.mapIndexed { index, lens -> (it.key + 1) * (index + 1) * lens.focalLength }.sum()
        }
    }

    private fun getHash(string: String) = string.fold(0) { acc, char -> acc.plus(char.code).times(17).mod(256) }

    private data class Lens(val label: String, val focalLength: Int)
}
