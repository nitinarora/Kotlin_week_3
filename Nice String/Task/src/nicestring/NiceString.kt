package nicestring

fun String.isNice(): Boolean {
    var conditionCount = 0
    val vowels = listOf('a', 'e', 'i', 'o', 'u')
    var vowelCount = 0
    var index = 0
    var duplicateExists = 0
    for (ch in this) {
        if(vowels.contains(ch))
            vowelCount++
        if(index < length-1 && ch == get(index +1)) duplicateExists++
        index++
    }
    if(vowelCount > 2) conditionCount++
    if(duplicateExists > 0) conditionCount++

    if(conditionCount == 2) return true

    if (!contains("bu", true) && !contains("ba", true) && !contains("be", true))
        conditionCount++

    return conditionCount > 1
}