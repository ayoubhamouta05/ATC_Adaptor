package com.youppix.atcadaptor.domain.model.calculations

import kotlinx.serialization.Serializable

@Serializable
data class CalculationsHistory(
    val calculation_id: Int,
    val created_at: String
)