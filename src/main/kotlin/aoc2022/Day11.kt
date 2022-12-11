package aoc2022

import java.io.File

fun main() {
    println(day11())
}

private const val FILE_PATH_DAY11 = "src/main/resources/aoc2022/day11.txt"

fun day11(filePath: String = FILE_PATH_DAY11): Pair<Long, Long> {
    val lines = File(filePath).readLines()
    var monkeys = parseMonkeys(lines)
    val solution1 = calculateMonkeyBusiness(monkeys, 3, 20)
    monkeys = parseMonkeys(lines)
    val solution2 = calculateMonkeyBusiness(monkeys, 1, 10000)
    return Pair(solution1, solution2)
}

internal fun calculateMonkeyBusiness(monkeys: List<Monkey>, relief: Int, rounds: Int): Long {
    val leastCommonMultiple = monkeys.fold(1) { acc, monkey -> acc * monkey.divisor }
    repeat(rounds) { executeRound(monkeys, relief, leastCommonMultiple) }
    val monkeysSortedByInspections = monkeys.sortedByDescending { it.numberOfInspections }
    return monkeysSortedByInspections[0].numberOfInspections * monkeysSortedByInspections[1].numberOfInspections
}

internal fun executeRound(monkeys: List<Monkey>, relief: Int, leastCommonMultiple: Int) {
    monkeys
        .forEach { monkey ->
            monkey.items.forEach { item ->
                var inspectedItem = monkey.operation.invoke(item)
                inspectedItem = if (relief > 1) {
                    inspectedItem / relief
                } else {
                    inspectedItem % leastCommonMultiple
                }
                monkey.numberOfInspections++
                if (inspectedItem % monkey.divisor == 0L) {
                    monkeys[monkey.targetTestTrue].items.add(inspectedItem)
                } else {
                    monkeys[monkey.targetTestFalse].items.add(inspectedItem)
                }
            }
            monkey.items.clear()
        }
}

internal data class Monkey(
    val items: MutableList<Long>,
    val operation: (oldItem: Long) -> Long,
    val divisor: Int,
    val targetTestTrue: Int,
    val targetTestFalse: Int,
    var numberOfInspections: Long = 0L,
)

internal fun parseMonkeys(lines: List<String>): List<Monkey> {
    return lines
        .chunked(7)
        .map { information ->
            val startingItems = information[1]
                .substringAfter("Starting items: ")
                .split(",").map { it.trim().toLong() }
                .toMutableList()
            val operatorAndFactor = information[2].split("old ").last().split(" ")
            val operation: (Long) -> Long = if (operatorAndFactor.first() == "+") {
                if (operatorAndFactor.last() == "old") {{it + it}} else {{it + operatorAndFactor.last().toLong()}}
            } else {
                if (operatorAndFactor.last() == "old") {{it * it}} else {{it * operatorAndFactor.last().toLong()}}
            }
            val divisor = information[3].split(" ").last().toString().toInt()
            val targetTestTrue = information[4].last().toString().toInt()
            val targetTestFalse = information[5].last().toString().toInt()
            Monkey(startingItems, operation, divisor, targetTestTrue, targetTestFalse)
        }
}
