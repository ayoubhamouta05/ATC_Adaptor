package com.youppix.atcadaptor.data.remote.home.dto

import com.youppix.atcadaptor.domain.model.home.HomeSearchItemData
import kotlinx.serialization.Serializable

@Serializable
data class HomeSearchResponse(
    val data: List<HomeSearchItemData>? = null,
    val message: String,
    val status: String
)