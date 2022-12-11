package aoc2022

import java.io.File

fun main() {
    println(day10())
}

private const val FILE_PATH_DAY10 = "src/main/resources/aoc2022/day10.txt"

fun day10(filePath: String = FILE_PATH_DAY10): Pair<Int, Unit> {
    val instructions = parseInstructions(File(filePath).readLines())
    val solution1 = determineSumSignalStrengths(instructions)
    val solution2 = printPixels(instructions)
    return Pair(solution1, solution2)
}

internal fun determineSumSignalStrengths(instructions: List<Pair<String, Int>>): Int {
    var sumSignalStrengths = 0
    executeProgramInstructions(instructions) { cycle, x ->
        if ((cycle - 20) % 40 == 0) {
            sumSignalStrengths += cycle * x
        }
    }
    return sumSignalStrengths
}

internal fun printPixels(instructions: List<Pair<String, Int>>) {
    executeProgramInstructions(instructions) { cycle, x ->
        val currentPixel = if (cycle % 40 == 0) 39 else cycle % 40 - 1
        if ((x - 1..x + 1).contains(currentPixel)) {
            print("#")
        } else {
            print(".")
        }
        if (currentPixel == 39) {
            println()
        }
    }
}

internal fun executeProgramInstructions(instructions: List<Pair<String, Int>>, func: (cycle: Int, x: Int) -> Unit) {
    var cycle = 1
    var x = 1
    instructions.forEach {
        func.invoke(cycle, x)
        cycle++
        if (it.first == "addx") {
            func.invoke(cycle, x)
            x += it.second
            cycle++
        }
    }
}

internal fun parseInstructions(lines: List<String>): List<Pair<String, Int>> {
    return lines
        .map {
            val signal = it.split(" ")
            Pair(it.substringBefore(" "), if (signal.size == 1) 0 else it.substringAfter(" ").toInt())
        }
}