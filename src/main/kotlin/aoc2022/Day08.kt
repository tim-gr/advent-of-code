package aoc2022

import java.io.File

fun main() {
    println(day08())
}

const val FILE_PATH_DAY08 = "src/main/resources/aoc2022/day08.txt"

fun day08(filePath: String = FILE_PATH_DAY08): Pair<Int, Int> {
    val lines = File(filePath).readLines()
    val forest = parseForest(lines)
    val solution1 = findVisibleTreesFromOutside(forest)
    val solution2 = findBestScenicScore(forest)
    return Pair(solution1, solution2)
}

internal fun findVisibleTreesFromOutside(forest: Array<IntArray>): Int {
    val trees = mutableSetOf<Tree>()
    for (x in forest.indices) {
        trees.add(Tree(x, 0))
        trees.add(Tree(x, forest[0].lastIndex))
    }
    for (y in forest[0].indices) {
        trees.add(Tree(0, y))
        trees.add(Tree(forest.lastIndex, y))
    }
    return trees
        .fold(listOf<List<Tree>>()) { acc, tree ->
            acc + findTreeLinesFromPosition(tree.x, tree.y, forest.lastIndex, forest[0].lastIndex)
        }
        .fold(setOf<Tree>()) { acc, treeLine ->
            acc + findVisibleTreesOfTreeLine(treeLine, forest)
        }.size
}

internal fun findBestScenicScore(forest: Array<IntArray>): Int {
    var bestScenicScore = 0
    forest.forEachIndexed { x, forestColumn ->
        forestColumn.forEachIndexed { y, treeHeight ->
            val treeLines = findTreeLinesFromPosition(x, y, forest.lastIndex, forestColumn.lastIndex)
            val scenicScore = treeLines.fold(1) { acc, treeLine ->
                acc * findVisibleTreesFromTree(treeLine, forest, treeHeight).size
            }
            if (scenicScore > bestScenicScore) {
                bestScenicScore = scenicScore
            }
        }
    }
    return bestScenicScore
}

internal fun findTreeLinesFromPosition(x: Int, y: Int, maxX: Int, maxY: Int): List<List<Tree>> {
    val rangeXPos = x..maxX
    val rangeXNeg = (0..x).reversed()
    val rangeYPos = y..maxY
    val rangeYNeg = (0..y).reversed()

    return mutableListOf(
        rangeXPos.fold(listOf()) { acc, rangePosition -> acc + Tree(rangePosition, y) },
        rangeXNeg.fold(listOf()) { acc, rangePosition -> acc + Tree(rangePosition, y) },
        rangeYPos.fold(listOf()) { acc, rangePosition -> acc + Tree(x, rangePosition) },
        rangeYNeg.fold(listOf()) { acc, rangePosition -> acc + Tree(x, rangePosition) },
    )
}

internal fun findVisibleTreesOfTreeLine(line: List<Tree>, array: Array<IntArray>): List<Tree> {
    var tallestTree = -1
    val result = mutableListOf<Tree>()
    line.forEach { tree ->
        val treeHeight = array[tree.x][tree.y]
        if (treeHeight > tallestTree) {
            result.add(tree)
            tallestTree = treeHeight
        }
    }
    return result
}

internal fun findVisibleTreesFromTree(line: List<Tree>, array: Array<IntArray>, treeHeight: Int): List<Tree> {
    val result = mutableListOf<Tree>()
    line.drop(1).forEach { tree ->
        val elementHeight = array[tree.x][tree.y]
        result.add(tree)
        if (elementHeight >= treeHeight) {
            return result
        }
    }
    return result
}

internal data class Tree(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        return other is Tree && x == other.x && y == other.y
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}

internal fun parseForest(lines: List<String>): Array<IntArray> {
    val array = Array(lines.size) { IntArray(lines.first().length) }
    lines.forEachIndexed { y, element ->
        element.forEachIndexed { x, treeSizeChar ->
            array[x][y] = treeSizeChar.toString().toInt()
        }
    }
    return array
}
