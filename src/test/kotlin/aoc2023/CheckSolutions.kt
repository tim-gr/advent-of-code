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

    @Test
    fun `2023 day 03 task 1`() {
        val input = readInputFile(year = 2023, day = 3)
        assertEquals(531932, Day03.task1(input))
    }

    @Test
    fun `2023 day 03 task 2`() {
        val input = readInputFile(year = 2023, day = 3)
        assertEquals(73646890, Day03.task2(input))
    }

    @Test
    fun `2023 day 04 task 1`() {
        val input = readInputFile(year = 2023, day = 4)
        assertEquals(18653, Day04.task1(input))
    }

    @Test
    fun `2023 day 04 task 2`() {
        val input = readInputFile(year = 2023, day = 4)
        assertEquals(5921508, Day04.task2(input))
    }
}
