package com.completewordproblems.fourthgrade

import android.content.Context
import com.completewordproblems.fourthgrade.models.*
import java.io.InputStream

object Wizard {

    lateinit var currentProblem: WordProblem
    var currentStudent: Student? = null
    var currentStrategyIndex = 0

    fun setCurrentProblem(context: Context) {
        currentProblem = getWordProblems(context).random()
    }

    fun getTransitionId() : Int {
        return if (currentStudent != null) {
            when (currentStudent!!.strategies[currentStrategyIndex]) {
                Strategy.READ_THE_PROBLEM -> R.id.action_practiceFragment_to_readTheProblemFragment
                Strategy.INSPECT_KEY_WORDS -> R.id.action_practiceFragment_to_defineKeyWordsFragment
                Strategy.WHAT_ARE_YOU_LOOKING_FOR -> R.id.action_practiceFragment_to_whatAreYouLookingForFragment
                Strategy.WHAT_INFORMATION_IS_NEEDED -> R.id.action_practiceFragment_to_whatInformationIsNeededFragment
                Strategy.DRAW_THE_SCENE -> R.id.action_practiceFragment_to_drawSceneFragment
                Strategy.WRITE_THE_EQUATION -> R.id.action_practiceFragment_to_createExpressionFragment
                Strategy.SOLVE_THE_PROBLEM -> R.id.action_practiceFragment_to_solveFragment
            }
        } else {
            -1
        }
    }

    fun onLogin(studentId: String) {
        //SW not using studentId yet
        val strategies = arrayListOf(
            Strategy.READ_THE_PROBLEM, Strategy.INSPECT_KEY_WORDS, Strategy.WHAT_ARE_YOU_LOOKING_FOR,
            Strategy.WHAT_INFORMATION_IS_NEEDED, Strategy.DRAW_THE_SCENE, Strategy.WRITE_THE_EQUATION, Strategy.SOLVE_THE_PROBLEM
        )
        currentStudent = Student("Scott", 10, 4, listOf(), listOf(), strategies)
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