package aoc2023

import util.coordinates.Coordinates
import util.io.println
import util.io.readExampleInputFile
import util.io.readInputFile

fun main() {
    val input = readInputFile(year = 2023, day = 11)
    Day11.task1(input).println()
    Day11.task2(input).println()

    // Examples
    val exampleInput = readExampleInputFile(year = 2023, day = 11)
    Day11.task1(exampleInput).println()
    Day11.task2(exampleInput, expansion = 10).println()
    Day11.task2(exampleInput, expansion = 100).println()
}

object Day11 {

    fun task1(input: List<String>) = getSumOfShortestPathsBetweenGalaxies(input, expansion = 2)

    fun task2(input: List<String>, expansion: Int = 1_000_000) = getSumOfShortestPathsBetweenGalaxies(input, expansion)

    private fun getSumOfShortestPathsBetweenGalaxies(input: List<String>, expansion: Int): Long {
        val (yExpansions, xExpansions) = getExpandingRowsAndColumns(input)
        val galaxies = getGalaxies(input)
        val galaxyPairs = getGalaxyPairs(galaxies)
        return galaxyPairs.sumOf { pairs ->
            val (smallerX, biggerX) = listOf(pairs.first.x, pairs.second.x).sorted()
            val (smallerY, biggerY) = listOf(pairs.first.y, pairs.second.y).sorted()
            val xExpansionsBetween = (smallerX..biggerX).count { it in xExpansions }
            val yExpansionsBetween = (smallerY..biggerY).count { it in yExpansions }
            (biggerX - smallerX).toLong() + (biggerY - smallerY) + (expansion - 1) * (xExpansionsBetween + yExpansionsBetween)
        }
    }

    private fun getExpandingRowsAndColumns(input: List<String>) = Pair(
        input.foldIndexed(emptyList<Int>()) { index, acc, row -> if (!row.contains("#")) acc + index else acc },
        input[0].indices.foldIndexed(emptyList<Int>()) { index, acc, column ->
            if (input.none { it[column] == '#' }) acc + index else acc
        }
    )

    private fun getGalaxies(input: List<String>): Set<Coordinates> {
        return input.foldIndexed(emptySet()) { y, accRow, row ->
            accRow + row.foldIndexed(emptySet()) { x, accColumn, element ->
                if (element == '#') accColumn + Coordinates(x, y) else accColumn
            }
        }
    }

    private fun getGalaxyPairs(galaxies: Set<Coordinates>): Set<Pair<Coordinates, Coordinates>> {
        val galaxyPairs = mutableSetOf<Pair<Coordinates, Coordinates>>()
        galaxies.forEach { galaxy1 ->
            galaxies.forEach { galaxy2 ->
                if (galaxy1 != galaxy2 && !galaxyPairs.contains(Pair(galaxy2, galaxy1))) {
                    galaxyPairs.add(Pair(galaxy1, galaxy2))
                }
            }
        }
        return galaxyPairs
    }
}
