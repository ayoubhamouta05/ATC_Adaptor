package com.youppix.atcadaptor.data.remote.alert.dto

import com.youppix.atcadaptor.domain.model.notification.NotificationsData
import kotlinx.serialization.Serializable

@Serializable
data class NotificationsResponse(
    val data : List<NotificationsData> ?= null,
    val message: String,
    val status: String
)

