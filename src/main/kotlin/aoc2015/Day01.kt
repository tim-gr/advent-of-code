package aoc2015

fun main() {
    val input = readInputFile(year = 2015, day = 1).first()
    Day01.task1(input).println()
    Day01.task2(input).println()
}

object Day01 {

    fun task1(input: String) = getLastFloor(input, stopInBasement = false)

    fun task2(input: String) = getLastFloor(input, stopInBasement = true)

    private fun getLastFloor(input: String, stopInBasement: Boolean): Int {
        var currentFloor = 0
        input.forEachIndexed { index, symbol ->
            when (symbol) {
                '(' -> currentFloor++
                ')' -> currentFloor--
                else -> error("Invalid input: $symbol")
            }
            if (currentFloor == -1 && stopInBasement) {
                return index + 1
            }
        }
        return currentFloor
    }
}
