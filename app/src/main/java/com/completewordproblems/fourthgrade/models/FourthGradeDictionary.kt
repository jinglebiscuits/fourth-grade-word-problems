package com.completewordproblems.fourthgrade.models

object FourthGradeDictionary {

    private val map: Map<String, FourthGradeWord> = mapOf(
        "recently" to FourthGradeWord(
            "recent",
            "during the period of time that has just passed : not long ago",
            arrayOf("of relatively late occurrence or appearance", "a long time ago")
        )
    )

    fun getWord(word: String): FourthGradeWord? {
        return map[word]
    }
}