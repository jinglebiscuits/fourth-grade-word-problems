package com.completewordproblems.fourthgrade

import com.completewordproblems.fourthgrade.models.WordProblem
import com.completewordproblems.fourthgrade.models.WordProblemSegment

object Wizard {

    fun getWordProblem(): WordProblem {
        val wordProblem: WordProblem = WordProblem()
        wordProblem.answer = "4"
        val text: List<WordProblemSegment> = listOf(
            WordProblemSegment(
                "Ashley and four friends recently went trick-or-treating.",
                isNecessary = true,
                isMainObjective = false
            ),
            WordProblemSegment(
                "Each of them got 4/5 of a bag of treats.",
                isNecessary = true,
                isMainObjective = false
            ),
            WordProblemSegment("How many bags of treats did they have in total?",
                isNecessary = true,
                isMainObjective = true
            )
        )
        wordProblem.segments = text

        return wordProblem
    }
}