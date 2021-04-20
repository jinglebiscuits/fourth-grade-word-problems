package com.completewordproblems.fourthgrade

import android.content.Context
import com.completewordproblems.fourthgrade.models.Concept
import com.completewordproblems.fourthgrade.models.KeyWord
import com.completewordproblems.fourthgrade.models.WordProblem
import com.completewordproblems.fourthgrade.models.WordProblemSegment
import java.io.InputStream

object Wizard {

    lateinit var currentProblem: WordProblem

    fun setCurrentProblem(context: Context) {
        currentProblem = getWordProblems(context).random()
    }

    fun getAshleyWordProblem(): WordProblem {
        val wordProblem = WordProblem()
        wordProblem.answer = "4"
        val segment1 = WordProblemSegment(
            "Ashley and four friends recently went trick-or-treating.",
            isNecessary = true,
            isMainObjective = false
        )
        val keyWord1 = KeyWord(24, 32, "recently", Concept(listOf(), 1, 1))
        segment1.addKeyword(keyWord1)
        val segment2 = WordProblemSegment(
            "Each of them got 4/5 of a bag of treats.",
            isNecessary = true,
            isMainObjective = false
        )
        val keyWord2 = KeyWord(17, 20, "4/5", Concept(listOf(), 1, 1))
        segment2.addKeyword(keyWord2)
        val segment3 = WordProblemSegment(
            "How many bags of treats did they have in total?",
            isNecessary = true,
            isMainObjective = true
        )
        val keyWord3 = KeyWord(41, 46, "total", Concept(listOf(), 1, 1))
        segment3.addKeyword(keyWord3)
        val text: List<WordProblemSegment> = listOf(
            segment1,
            segment2,
            segment3
        )
        wordProblem.segments = text

        return wordProblem
    }

    fun getWordProblems(context: Context): List<WordProblem> {
        val assetManager = context.assets
        val inputStream: InputStream = assetManager.open("word_problems.xml")
        val parser = WordProblemXmlParser()
        return parser.parse(inputStream)
    }
}