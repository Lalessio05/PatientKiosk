package com.lalessio.patientkiosk.domain

import kotlinx.serialization.Serializable

@Serializable
data class AnswerOption(
    val label: String,
    val points: Int,
)

data class Question(
    val text: String,
    val options: List<AnswerOption>,
    val subscaleKey: String? = null,
)

data class Subscale(
    val key: String,
    val label: String,
    val maxScore: Int,
)

/**Estremi inclusivi**/
data class Band(
    val min: Int,
    val max: Int,
    val label: String,
    val note: String,
) {
    fun contains(score: Int): Boolean = score in min..max
}


data class Questionnaire(
    val id: String,
    val name: String,
    val description: String,
    val recall: String,
    val scale: Int,
    val maxScore: Int,
    val source: String,
    val questions: List<Question>,
    val subscales: List<Subscale> = emptyList(),
    val bands: List<Band> = emptyList(),
) {
    val hasSubscales: Boolean get() = subscales.isNotEmpty()
}