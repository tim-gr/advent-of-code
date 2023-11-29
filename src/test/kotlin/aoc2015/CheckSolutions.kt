package aoc2015

import org.junit.jupiter.api.Test
import util.io.readInputFile
import kotlin.test.assertEquals

class CheckSolutions {

    @Test
    fun `2015 day 01 task 1`() {
        val input = readInputFile(year = 2015, day = 1).first()
        assertEquals(138, Day01.task1(input))
    }

    @Test
    fun `2015 day 01 task 2`() {
        val input = readInputFile(year = 2015, day = 1).first()
        assertEquals(1771, Day01.task2(input))
    }

    @Test
    fun `2015 day 02 task 1`() {
        val input = readInputFile(year = 2015, day = 2)
        assertEquals(1598415, Day02.task1(input))
    }

    @Test
    fun `2015 day 02 task 2`() {
        val input = readInputFile(year = 2015, day = 2)
        assertEquals(3812909, Day02.task2(input))
    }

    @Test
    fun `2015 day 03 task 1`() {
        val input = readInputFile(year = 2015, day = 3).first()
        assertEquals(2592, Day03.task1(input))
    }

    @Test
    fun `2015 day 03 task 2`() {
        val input = readInputFile(year = 2015, day = 3).first()
        assertEquals(2360, Day03.task2(input))
    }
}
