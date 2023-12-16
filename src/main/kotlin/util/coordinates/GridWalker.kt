package util.coordinates

class GridWalker(
    rows: List<String>,
    startPosition: Coordinates,
    startDirection: Direction
) {
    private val array = rows.map { it.toCharArray() }.toTypedArray()

    private var currentX = startPosition.x
    private var currentY = startPosition.y
    val currentPosition get() = Coordinates(currentX, currentY)
    var currentDirection = startDirection
        private set
    val charOnCurrentPosition get() = array[currentY][currentX]

    fun moveIntoSameDirection(steps: Int = 1) = when(currentDirection) {
        Direction.NORTH -> moveNorth(steps)
        Direction.SOUTH -> moveSouth(steps)
        Direction.WEST -> moveWest(steps)
        Direction.EAST -> moveEast(steps)
    }

    fun moveNorth(steps: Int = 1) {
        if (currentY - steps > -1) {
            currentY -= steps
            currentDirection = Direction.NORTH
        }
    }

    fun moveSouth(steps: Int = 1) {
        if (currentY + steps < array.size) {
            currentY += steps
            currentDirection = Direction.SOUTH
        }
    }

    fun moveWest(steps: Int = 1) {
        if (currentX - steps > -1) {
            currentX -= steps
            currentDirection = Direction.WEST
        }
    }

    fun moveEast(steps: Int = 1) {
        if (currentX + steps < array[0].size) {
            currentX += steps
            currentDirection = Direction.EAST
        }
    }
}
