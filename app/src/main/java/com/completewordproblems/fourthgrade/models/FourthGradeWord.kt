package com.completewordproblems.fourthgrade.models

data class FourthGradeWord(
    val word: String,
    val correctDefinition: String,
    val incorrectDefinitions: Array<String>
)
