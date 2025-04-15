package com.youppix.atcadaptor.data.remote.calculation.dto

import com.youppix.atcadaptor.domain.model.calculations.CalculationData
import kotlinx.serialization.Serializable

@Serializable
data class GetCalculationByIdResponse(
    val data: CalculationData,
    val message: String,
    val status: String
)