package util.grid

fun drawCoordinates(data: Set<CoordinatesWithChar>) {
    val maxX = data.maxOf { it.x }
    val maxY = data.maxOf { it.y }
    val minX = data.minOf { it.x }
    val minY = data.minOf { it.y }
    val map = data.groupBy { it.y }
    for (y in maxY downTo minY) {
        val row = map[y]
        val stringBuilder = StringBuilder()
        stringBuilder.append(".".repeat(1 + maxX - minX))
        row?.sortedBy { it.x }?.forEach {
            if (stringBuilder[it.x - minX] != 'S') {
                stringBuilder.setCharAt(it.x - minX, it.content)
            }
        }
        print(stringBuilder.toString().replace(".".toRegex(), "$0 "))
        println(y)
    }
    var xCounter = minX
    while (xCounter % 5 != 0) {
        print("  ")
        xCounter++
    }
    while (xCounter < maxX) {
        print(xCounter)
        repeat(10 - xCounter.toString().length) {
            print(" ")
        }
        xCounter += 5
    }
}

fun<T> drawCoordinates(data: Array<Array<T>>, transform: (T) -> Char) {
    require(data.isNotEmpty()) { "The parameter <data> cannot be an empty list." }
    for (i in 0 until data[0].size) {
        data.forEach { column -> print(transform.invoke(column[i])) }
        println()
    }
    println()
}

data class CoordinatesWithChar(val x: Int, val y: Int, val content: Char = 'X')
