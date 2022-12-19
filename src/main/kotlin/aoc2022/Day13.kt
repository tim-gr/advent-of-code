package aoc2022

import util.string.splitAndKeepDelimiters
import java.io.File

fun main() {
    println(day13())
}

fun day13(): Pair<Int, Int> {
    val packets = parsePackets(File("src/main/resources/aoc2022/day13.txt").readLines())
    val solution1 = getIndicesSumOfCorrectlyOrderedPackages(packets)
    val solution2 = getDecoderKey(packets)
    return Pair(solution1, solution2)
}

internal fun getIndicesSumOfCorrectlyOrderedPackages(packets: List<ListPacketData>): Int {
    return packets
        .chunked(2)
        .mapIndexed { index, packetsPair ->
            if (comparePacketData(packetsPair[0], packetsPair[1]) == PacketsComparisonResult.CORRECT) index + 1 else 0
        }.sum()
}

internal fun getDecoderKey(packets: List<ListPacketData>): Int {
    val divider1 = parsePacket("[[2]]")
    val divider2 = parsePacket("[[6]]")
    val packetsInCorrectOrder = mutableListOf<PacketData>()
    packets.plus(divider1).plus(divider2).forEach { packet ->
        for (i in packetsInCorrectOrder.indices) {
            if (comparePacketData(packet, packetsInCorrectOrder[i]) == PacketsComparisonResult.CORRECT) {
                packetsInCorrectOrder.add(i, packet)
                return@forEach
            }
        }
        packetsInCorrectOrder.add(packet)
    }
    val indexDivider1 = packetsInCorrectOrder.indexOf(divider1) + 1
    val indexDivider2 = packetsInCorrectOrder.indexOf(divider2) + 1
    return indexDivider1 * indexDivider2
}

internal fun comparePacketData(left: PacketData, right: PacketData): PacketsComparisonResult {
    return if (left is NumberPacketData && right is NumberPacketData) {
        compareNumbers(left.number, right.number)
    } else if (left is ListPacketData && right is ListPacketData) {
        compareLists(left.items, right.items)
    } else {
        compareNumberAndList(left, right)
    }
}

private fun compareNumbers(left: Int, right: Int): PacketsComparisonResult {
    return if (left < right) {
        PacketsComparisonResult.CORRECT
    } else if (left == right) {
        PacketsComparisonResult.CONTINUE
    } else {
        PacketsComparisonResult.WRONG
    }
}

private fun compareLists(left: List<PacketData>, right: List<PacketData>): PacketsComparisonResult {
    left.zip(right).forEach {
        val result = comparePacketData(it.first, it.second)
        if (result != PacketsComparisonResult.CONTINUE) {
            return result
        }
    }
    return if (left.size < right.size) {
        PacketsComparisonResult.CORRECT
    } else if (left.size == right.size) {
        PacketsComparisonResult.CONTINUE
    } else {
        PacketsComparisonResult.WRONG
    }
}

private fun compareNumberAndList(left: PacketData, right: PacketData): PacketsComparisonResult {
    val leftItem = if (left is NumberPacketData) ListPacketData(listOf(NumberPacketData(left.number))) else left
    val rightItem = if (right is NumberPacketData) ListPacketData(listOf(NumberPacketData(right.number))) else right
    return comparePacketData(leftItem, rightItem)
}

internal interface PacketData
internal class NumberPacketData(val number: Int) : PacketData
internal class ListPacketData(val items: List<PacketData>) : PacketData

internal enum class PacketsComparisonResult {
    CORRECT, WRONG, CONTINUE
}

internal fun parsePackets(lines: List<String>): List<ListPacketData> {
    return lines
        .filter { it.isNotEmpty() }
        .map { parsePacket(it) }
}

internal fun parsePacket(packetString: String): ListPacketData {
    val parts = packetString.splitAndKeepDelimiters(',', '[', ']').toMutableList()
    val result = mutableListOf<PacketData>()
    while (parts.isNotEmpty()) {
        val current = parts.removeFirst()
        if (current == "[") {
            result.add(parsePacket(parts.joinToString("")))
            removeAlreadyParsedParts(parts)
        } else if (current == "]") {
            return ListPacketData(result)
        } else if (current.toIntOrNull() != null) {
            result.add(NumberPacketData(current.toInt()))
        } else if (current == ",") {
            // No action necessary
        } else {
            throw UnsupportedOperationException("This character cannot be parsed: $current")
        }
    }
    return ListPacketData(result)
}

private fun removeAlreadyParsedParts(parts: MutableList<String>) {
    var numberOfOpenBrackets = 1
    while (parts.isNotEmpty()) {
        when (parts.removeFirst()) {
            "[" -> numberOfOpenBrackets++
            "]" -> numberOfOpenBrackets--
            else -> Unit
        }
        if (numberOfOpenBrackets == 0) {
            break
        }
    }
}
