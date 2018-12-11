package nicestring

fun String.isNice(): Boolean {
    fun Char.isVowel() = this in listOf('a', 'e', 'i', 'o', 'u')

    var conditionCount = 0

    this.forEachIndexed { index, c ->
        if (index < length - 1 && c == get(index + 1)) {
            if(conditionCount == 0)conditionCount++
        }
    }
    if (this.filter { it.isVowel() }.count() > 2) conditionCount++
    if (conditionCount == 2) return true

    if (!contains("bu", true) && !contains("ba", true) && !contains("be", true))
        conditionCount++

    return conditionCount > 1
}