package util.string

fun String.splitAndKeepDelimiters(vararg delimiters: Char): List<String> {
    val result = mutableListOf<String>()
    val sb = StringBuilder()
    this.toCharArray().forEach {
        if (delimiters.contains(it)) {
            if (sb.isNotEmpty()) {
                result.add(sb.toString())
            }
            sb.clear()
            result.add(it.toString())
        } else {
            sb.append(it)
        }
    }
    return result
}