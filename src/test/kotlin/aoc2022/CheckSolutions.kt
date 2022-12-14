package aoc2022

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CheckSolutions {

    @Test
    fun testDay01() {
        val day01 = day01()
        assertEquals(71506, day01.first)
        assertEquals(209603, day01.second)
    }

    @Test
    fun testDay02() {
        val day02 = day02()
        assertEquals(15632, day02.first)
        assertEquals(14416, day02.second)
    }

    @Test
    fun testDay03() {
        val day03 = day03()
        assertEquals(8139, day03.first)
        assertEquals(2668, day03.second)
    }

    @Test
    fun testDay04() {
        val day04 = day04()
        assertEquals(602, day04.first)
        assertEquals(891, day04.second)
    }

    @Test
    fun testDay05() {
        val day05 = day05()
        assertEquals("VCTFTJQCG", day05.first)
        assertEquals("GCFGLDNJZ", day05.second)
    }

    @Test
    fun testDay06() {
        val day06 = day06()
        assertEquals(1965, day06.first)
        assertEquals(2773, day06.second)
    }

    @Test
    fun testDay07() {
        val day07 = day07()
        assertEquals(1453349, day07.first)
        assertEquals(2948823, day07.second)
    }

    @Test
    fun testDay10() {
        val day10 = day10()
        assertEquals(13860, day10.first)
        // The second part of this task is only printed in the console
    }

    @Test
    fun testDay11() {
        val day11 = day11()
        assertEquals(117640, day11.first)
        assertEquals(30616425600, day11.second)
    }

    @Test
    fun testDay12() {
        val day12 = day12()
        assertEquals(339, day12.first)
        assertEquals(332, day12.second)
    }
}