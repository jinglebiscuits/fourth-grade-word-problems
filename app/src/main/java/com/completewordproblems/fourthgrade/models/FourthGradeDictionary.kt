package com.completewordproblems.fourthgrade.models

object FourthGradeDictionary {

    private val map: Map<String, FourthGradeWord> = mapOf(
        "recently" to FourthGradeWord(
            "recent",
            "during the period of time that has just passed : not long ago",
            arrayOf("of relatively late occurrence or appearance", "a long time ago")
        ), "simplest form" to FourthGradeWord(
            "simplest form",
            "1 is the only common factor of the numerator and denominator",
            arrayOf("The numerator is divisible by the denominator", "The denominator is divisible by the numerator")
        ), "equal parts" to FourthGradeWord(
            "equal parts", "Division resulting in identical parts.", arrayOf("Two fractions with equal numerators.", "Two fractions with equal denominators.")
        )
    )

    fun getWord(word: String): FourthGradeWord? {
        return map[word]
    }
}