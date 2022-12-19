package aoc2022

import java.io.File
import kotlin.math.abs

fun main() {
    println(day15())
}

private const val FILE_PATH_DAY15 = "src/main/resources/aoc2022/day15.txt"
private const val LINE = 2000000

fun day15(filePath: String = FILE_PATH_DAY15): Pair<Int, Int> {
    val sensors = parseSensorsAndBeacons(File(filePath).readLines())
    val placesWithoutBeacon = findPlacesWithoutBeacon(sensors, LINE)
    val solution1 = placesWithoutBeacon.size
    val solution2 = findNotCoveredPosition(placesWithoutBeacon)
    return Pair(solution1, solution2)
}

fun findNotCoveredPosition(placesWithoutBeacon: Set<CavePosition>): Int {
    placesWithoutBeacon.sortedBy { it.x }.sortedBy { it.y }
    for (x in 0..4000000) {
        for (y in 0..4000000) {

        }
    }
    return 0
}

fun findPlacesWithoutBeacon(sensors: List<Sensor>, line: Int): Set<CavePosition> {
    val placesWithoutBeacon = mutableSetOf<CavePosition>()
    sensors.forEach { sensor ->
        val distanceToBeacon = sensor.findDistanceToBeacon()
        for (x in ((distanceToBeacon * -1) + sensor.coordinates.x)..(distanceToBeacon + sensor.coordinates.x)) {
            val minY = (distanceToBeacon - abs(x - sensor.coordinates.x)) * -1 + sensor.coordinates.y
            val maxY = distanceToBeacon - abs(x - sensor.coordinates.x) + sensor.coordinates.y
            if ((minY..maxY).contains(line)) {
                placesWithoutBeacon.add(CavePosition(x, line))
            }
        }
    }
    sensors.map { it.beaconCoordinates }.forEach { placesWithoutBeacon.remove(it) }
    return placesWithoutBeacon
}

fun parseSensorsAndBeacons(lines: List<String>): List<Sensor> {
    return lines.map {
        val values = Regex("-?[0-9]+").findAll(it).toList()
        Sensor(
            CavePosition(values[0].value.toInt(), values[1].value.toInt()),
            CavePosition(values[2].value.toInt(), values[3].value.toInt())
        )
    }
}

data class Sensor(val coordinates: CavePosition, val beaconCoordinates: CavePosition) {
    fun findDistanceToBeacon(): Int {
        return abs(this.coordinates.x - this.beaconCoordinates.x) + abs(this.coordinates.y - this.beaconCoordinates.y)
    }
}