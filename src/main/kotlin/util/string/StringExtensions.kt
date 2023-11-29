package util.string

fun String.splitAndKeepDelimiters(vararg delimiters: Char): List<String> {
    val result = mutableListOf<String>()
    val sb = StringBuilder()
    forEach {
        if (it in delimiters) {
            if (sb.isNotEmpty()) {
                result.add(sb.toString())
                sb.clear()
            }
            result.add(it.toString())
        } else {
            sb.append(it)
        }
    }
    if (sb.isNotEmpty()) {
        result.add(sb.toString())
    }
    return result
}
