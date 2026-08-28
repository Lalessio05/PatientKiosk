package com.lalessio.patientkiosk.data.local.dao

data class QuestionnaireSummaryRow(
    val id: String, val name: String, val description: String,
    val source: String, val questionCount: Int,
)
