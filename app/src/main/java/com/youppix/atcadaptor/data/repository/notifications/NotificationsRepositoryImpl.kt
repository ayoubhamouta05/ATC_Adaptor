package com.youppix.atcadaptor.data.repository.notifications

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.alert.AlertService

import com.youppix.atcadaptor.data.remote.alert.dto.NotificationsResponse
import com.youppix.atcadaptor.domain.repository.notifications.NotificationsRepository
import kotlinx.coroutines.flow.Flow

class NotificationsRepositoryImpl(private val service: AlertService) : NotificationsRepository {
    override  fun getNotifications(userId: Int): Flow<Resource<NotificationsResponse>> {
        return service.getNotifications(userId = userId)
    }

    override  fun updateNotification(userId: Int){
        service.updateNotifications(userId = userId)
    }
}