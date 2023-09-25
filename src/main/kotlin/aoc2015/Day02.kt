package aoc2015

fun main() {
    val input = readInputFile(year = 2015, day = 2)
    Day02.task1(input).println()
    Day02.task2(input).println()
}

object Day02 {

    fun task1(input: List<String>): Int {
        return input.sumOf { line ->
            val (length, width, height) = line.split("x").map { it.toInt() }
            calculateWrappingPaperForSinglePresent(length, width, height)
        }
    }

    fun task2(input: List<String>): Int {
        return input.sumOf { line ->
            val (length, width, height) = line.split("x").map { it.toInt() }
            2 * minOf(length + width, width + height , height + length) + length * width * height
        }
    }

    private fun calculateWrappingPaperForSinglePresent(length: Int, width: Int, height: Int): Int {
        val area1 = length * width
        val area2 = width * height
        val area3 = height * length
        return 2 * area1 + 2 * area2 + 2 * area3 + minOf(area1, area2, area3)
    }
}
