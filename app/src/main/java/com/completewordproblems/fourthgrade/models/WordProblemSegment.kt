package com.completewordproblems.fourthgrade.models

class WordProblemSegment(
    val segment: String,
    val isNecessary: Boolean,
    val isMainObjective: Boolean
) {
    val keyWords = mutableListOf<KeyWord>()

    fun addKeyword(keyWord: KeyWord) {
        keyWords.add(keyWord)
    }
}
