package com.youppix.atcadaptor.domain.useCases.notifications


data class NotificationsUseCases(
    val getNotifications: GetNotificationsUseCase,
    val updateNotification : UpdateNotificationUseCase ,
    val sendNotification : SendNotificationUseCase
)
