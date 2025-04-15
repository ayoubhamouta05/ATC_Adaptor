package com.youppix.atcadaptor.data.remote.calculation.dto

import com.youppix.atcadaptor.domain.model.calculations.CalculationsHistory
import kotlinx.serialization.Serializable

@Serializable
data class GetCalculationsHistoryResponse(
    val data: List<CalculationsHistory>,
    val message: String,
    val status: String
)