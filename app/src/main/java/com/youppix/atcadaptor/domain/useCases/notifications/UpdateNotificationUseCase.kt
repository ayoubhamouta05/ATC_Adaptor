package com.youppix.atcadaptor.domain.useCases.notifications

import com.youppix.atcadaptor.domain.repository.notifications.NotificationsRepository

class UpdateNotificationUseCase (
    private val notificationsRepository: NotificationsRepository
) {
     operator fun invoke(userId : Int) =
        notificationsRepository.updateNotification(userId = userId)


}