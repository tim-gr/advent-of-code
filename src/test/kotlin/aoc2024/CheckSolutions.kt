package aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.io.readInputFile

class CheckSolutions {

    @Test
    fun `2024 day 01 task 1`() {
        val input = readInputFile(year = 2024, day = 1)
        assertEquals(1151792, Day01.task1(input))
    }

    @Test
    fun `2024 day 01 task 2`() {
        val input = readInputFile(year = 2024, day = 1)
        assertEquals(21790168, Day01.task2(input))
    }
}
