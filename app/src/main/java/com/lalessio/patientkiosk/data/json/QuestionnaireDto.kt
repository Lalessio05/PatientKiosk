package com.lalessio.patientkiosk.data.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//Data transfer object per convertire da json a record di DB (e viceversa)

@Serializable
data class QuestionnaireFileDto(
    val version: String,
    val questionnaires: List<QuestionnaireDto>,
)

@Serializable
data class QuestionnaireDto(
    val id: String,
    val name: String,
    //SerialName è il nome nel json
    @SerialName("desc") val description: String,
    val recall: String,
    val scale: Int,
    @SerialName("max") val maxScore: Int,
    val source: String,
    val questions: List<QuestionDto>,
    //Assente in HADS: ogni domanda ha le sue opzioni
    val options: List<OptionDto> = emptyList(),
    //Presente solo in HADS
    val subscales: List<SubscaleDto> = emptyList(),
    val bands: List<BandDto> = emptyList(),
)

@Serializable
data class QuestionDto(
    val text: String,
    //Chiave della sottoscala, null se il questionario non ne ha
    val subscale: String? = null,
    //Null se la domanda usa le options comuni del questionario
    val options: List<OptionDto>? = null,
)

@Serializable
data class OptionDto(
    val label: String,
    val points: Int,
)

@Serializable
data class SubscaleDto(
    val key: String,
    val label: String,
    @SerialName("max") val maxScore: Int,
)

@Serializable
data class BandDto(
    val label: String,
    val min: Int,
    val max: Int,
    val note: String,
)