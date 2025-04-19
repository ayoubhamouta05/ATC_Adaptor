package com.youppix.atcadaptor.domain.useCases.notifications

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.dto.StandardResponse
import com.youppix.atcadaptor.domain.repository.notifications.NotificationsRepository
import kotlinx.coroutines.flow.Flow

class SendNotificationUseCase(
    private val notificationsRepository: NotificationsRepository
) {
    operator fun invoke(
        topic: String,
        title: String,
        body: String,
        sender: String
    ): Flow<Resource<StandardResponse>> =
        notificationsRepository.sendNotification (topic = topic, title = title, body = body, sender = sender)


}