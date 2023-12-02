package aoc2023

import org.junit.jupiter.api.Test
import util.io.readInputFile
import kotlin.test.assertEquals

class CheckSolutions {

    @Test
    fun `2023 day 01 task 1`() {
        val input = readInputFile(year = 2023, day = 1)
        assertEquals(55386, Day01.task1(input))
    }

    @Test
    fun `2023 day 01 task 2`() {
        val input = readInputFile(year = 2023, day = 1)
        assertEquals(54824, Day01.task2(input))
    }

    @Test
    fun `2023 day 02 task 1`() {
        val input = readInputFile(year = 2023, day = 2)
        assertEquals(2101, Day02.task1(input))
    }

    @Test
    fun `2023 day 02 task 2`() {
        val input = readInputFile(year = 2023, day = 2)
        assertEquals(58269, Day02.task2(input))
    }
}
