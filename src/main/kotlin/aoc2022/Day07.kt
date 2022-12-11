package aoc2022

import java.io.File

fun main() {
    println(day07())
}

const val FILE_PATH_DAY07 = "src/main/resources/aoc2022/day07.txt"

fun day07(filePath: String = FILE_PATH_DAY07): Pair<Int, Int> {
    val directoryStructure = parseDirectoryStructure(File(filePath).readLines())
    val solution1 = determineSizeOfDirectoriesWithSizeLowerThan(directoryStructure, 100000)
    val solution2 = determineSmallestDirectoryLargerThanNecessarySpace(directoryStructure, 30000000, 70000000)
    return Pair(solution1, solution2)
}

internal fun determineSizeOfDirectoriesWithSizeLowerThan(directoryStructure: Directory, lowerThan: Int): Int {
    return directoryStructure.findDirectoriesBySizeCriteria { size -> size < lowerThan }
        .sumOf { it.determineDirectorySize() }
}

internal fun determineSmallestDirectoryLargerThanNecessarySpace(directoryStructure: Directory, desiredSpace: Int, maxSpace: Int): Int {
    val necessarySpace = desiredSpace - (maxSpace - directoryStructure.determineDirectorySize())
    return directoryStructure.findDirectoriesBySizeCriteria { size -> size >= necessarySpace }
        .minOf { it.determineDirectorySize() }
}

internal class Directory(val name: String, val parentDirectory: Directory?) {

    private var directories = mutableListOf<Directory>()
    private var files = mutableListOf<FileItem>()

    fun determineDirectorySize(): Int {
        return files.sumOf { it.size } + directories.sumOf { it.determineDirectorySize() }
    }

    fun findDirectoriesBySizeCriteria(criteria: (Int) -> Boolean): List<Directory> {
        val relevantDirectories = mutableListOf<Directory>()
        if (criteria.invoke(determineDirectorySize())) {
            relevantDirectories.add(this)
        }
        directories.forEach {
            relevantDirectories.addAll(it.findDirectoriesBySizeCriteria(criteria))
        }
        return relevantDirectories
    }

    fun getDirectories() = directories

    fun addDirectory(directory: Directory) {
        if (directories.find { it.name == directory.name } == null) {
            directories.add(directory)
        }
    }

    fun addFile(file: FileItem) {
        if (files.find { it.name == file.name } == null ) {
            files.add(file)
        }
    }
}

internal data class FileItem(val name: String, val size: Int)

internal fun parseDirectoryStructure(lines: List<String>): Directory {
    val rootDirectory = Directory("/.", null)
    var currentDirectory = rootDirectory
    lines.forEach { line ->
        if (line == "$ cd ..") {
            if (currentDirectory.parentDirectory != null) {
                currentDirectory = currentDirectory.parentDirectory!!
            }
        } else if (line == "$ cd /") {
            currentDirectory = rootDirectory
        } else if (line == "$ ls") {
            // No action necessary
        } else if (line.startsWith("$ cd")) {
            val splitData = line.split(" ")
            currentDirectory = currentDirectory.getDirectories().find{it.name == splitData.last()}!!
        } else if (line.startsWith("dir")) {
            val splitData = line.split(" ")
            currentDirectory.addDirectory(Directory(splitData.last(), currentDirectory))
        } else {
            val splitData = line.split(" ")
            currentDirectory.addFile(FileItem(splitData.last(), splitData.first().toInt()))
        }
    }
    return rootDirectory
}