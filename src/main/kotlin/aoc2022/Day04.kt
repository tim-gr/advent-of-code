package aoc2022

import java.io.File

fun main() {
    println(day04())
}

private const val FILE_PATH_DAY04 = "src/main/resources/aoc2022/day04.txt"

fun day04(filePath: String = FILE_PATH_DAY04): Pair<Int, Int> {
    val sectionPairs = parseSectionPairs(File(filePath).readLines())
    val solution1 = calculateNumberOfFullyIncludedRanges(sectionPairs)
    val solution2 = calculateNumberOfOverlaps(sectionPairs)
    return Pair(solution1, solution2)
}

internal fun calculateNumberOfFullyIncludedRanges(sectionPairs: List<SectionPair>): Int {
    return sectionPairs.count {
            it.section1.contains(it.section2.start) && it.section1.contains(it.section2.endInclusive) ||
                    it.section2.contains(it.section1.start) && it.section2.contains(it.section1.endInclusive)
        }
}

internal fun calculateNumberOfOverlaps(sectionPairs: List<SectionPair>): Int {
    return sectionPairs.count { it.section1.contains(it.section2.start) || it.section2.contains(it.section1.start) }
}

internal data class SectionPair(val section1: ClosedRange<Int>, val section2: ClosedRange<Int>)

internal fun parseSectionPairs(lines: List<String>): List<SectionPair> {
    return lines
        .map { it.split("-", ",") }
        .map { SectionPair(it[0].toInt()..it[1].toInt(), it[2].toInt()..it[3].toInt()) }
}