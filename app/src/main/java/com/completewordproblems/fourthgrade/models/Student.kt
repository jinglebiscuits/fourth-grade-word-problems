package com.completewordproblems.fourthgrade.models

data class Student(
    var name: String,
    var age: Int,
    var grade: Int,
    val activeConcepts: List<Concept>,
    val masteredConcepts: List<Concept>,
    var strategies: List<Strategy>
)
