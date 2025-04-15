package com.youppix.atcadaptor.data.remote.calculation.dto

import com.youppix.atcadaptor.domain.model.medicament.Medicament
import kotlinx.serialization.Serializable

@Serializable
data class GetMedicamentResponse(
    val data: List<Medicament>,
    val message: String,
    val status: String
)