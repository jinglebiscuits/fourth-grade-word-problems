package com.completewordproblems.fourthgrade.models

data class FourthGradeWord(
    val word: String,
    val correctDefinition: String,
    val incorrectDefinitions: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FourthGradeWord

        if (word != other.word) return false
        if (correctDefinition != other.correctDefinition) return false
        if (!incorrectDefinitions.contentEquals(other.incorrectDefinitions)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = word.hashCode()
        result = 31 * result + correctDefinition.hashCode()
        result = 31 * result + incorrectDefinitions.contentHashCode()
        return result
    }
}
