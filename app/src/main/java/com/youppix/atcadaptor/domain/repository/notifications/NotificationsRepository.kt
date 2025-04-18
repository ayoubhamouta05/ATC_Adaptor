package com.youppix.atcadaptor.domain.repository.notifications


import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.alert.dto.NotificationsResponse

import kotlinx.coroutines.flow.Flow

interface NotificationsRepository  {

     fun getNotifications(userId : Int) : Flow<Resource<NotificationsResponse>>
     fun updateNotification(userId : Int)

}