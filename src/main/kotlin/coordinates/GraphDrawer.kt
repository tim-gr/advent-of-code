package coordinates

fun drawCoordinates(data: Set<Coordinates>) {
    val maxX = data.maxOf { it.x } + 1
    val maxY = data.maxOf { it.y } + 1
    val map = data.groupBy { it.y }
    for (y in 0 until maxY) {
        val row = map[y]
        val rowString = if (row == null) {
            ".".repeat(maxX)
        } else {
            val stringBuilder = StringBuilder(".".repeat(maxX))
            row.sortedBy { it.x }.forEach { stringBuilder.setCharAt(it.x, it.content) }
            stringBuilder.toString()
        }
        println(rowString.replace(".".toRegex(), "$0 "))
    }
}

data class Coordinates(val x: Int, val y: Int, val content: Char)