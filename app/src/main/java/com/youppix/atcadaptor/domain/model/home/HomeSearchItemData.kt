package com.youppix.atcadaptor.domain.model.home

import kotlinx.serialization.Serializable

@Serializable
data class HomeSearchItemData(
    val auc: String?,
    val dfg: String?,
    val dose: String?,
    val medicationName: String?,
    val patientId: Int ?= null,
    val patientName: String?= null,
    val toxicity: String?
)