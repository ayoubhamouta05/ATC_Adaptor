package com.youppix.atcadaptor.domain.useCases.notifications

import com.youppix.atcadaptor.domain.repository.notifications.NotificationsRepository

class GetNotificationsUseCase(
    private val notificationsRepository: NotificationsRepository
) {
     operator fun invoke(userId : Int) =
         notificationsRepository.getNotifications(userId = userId)


}