package com.completewordproblems.fourthgrade.models

class WordProblem {
    val id: Int = 1
    lateinit var segments: List<WordProblemSegment>
    lateinit var keyWords: List<KeyWord>
    lateinit var answer: String
    lateinit var concepts: List<Concept>

    fun getNecessaryInformation(): List<WordProblemSegment> {
        return segments.filter { it.isNecessary }
    }

    fun getUnnecessaryInformation(): List<WordProblemSegment> {
        return segments.filter { !it.isNecessary }
    }

    fun getMainObjective(): WordProblemSegment {
        return segments.first { it.isMainObjective }
    }

    fun getWordProblemText(): String {
        var wordProblemText = ""
        segments.forEach(fun(it: WordProblemSegment) {
            wordProblemText = wordProblemText.plus(it.segment + " ")
        })
        return wordProblemText.dropLast(1)
    }
}