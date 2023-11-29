package aoc2023

import org.junit.jupiter.api.Test
import util.io.readInputFile
import kotlin.test.assertEquals

class CheckSolutions {

    @Test
    fun `2023 day 01 task 1`() {
        val input = readInputFile(year = 2023, day = 1)
        assertEquals(0, Day01.task1(input))
    }

    @Test
    fun `2023 day 01 task 2`() {
        val input = readInputFile(year = 2023, day = 1)
        assertEquals(0, Day01.task2(input))
    }
}
