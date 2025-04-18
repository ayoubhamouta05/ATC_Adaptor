package com.youppix.atcadaptor.data.remote.calculation.dto

import com.youppix.atcadaptor.domain.model.user.UserDataResponse
import kotlinx.serialization.Serializable

@Serializable
data class GetUsersResponse(
    val status : String ,
    val message  : String ,
    val users : List<UserDataResponse>? = null
)
