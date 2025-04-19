package com.youppix.atcadaptor.domain.repository.notifications


import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.alert.dto.NotificationsResponse
import com.youppix.atcadaptor.data.remote.dto.StandardResponse

import kotlinx.coroutines.flow.Flow

interface NotificationsRepository  {

     fun getNotifications(userId : Int) : Flow<Resource<NotificationsResponse>>
     fun sendNotification(topic : String ,title : String , body : String , sender: String) : Flow<Resource<StandardResponse>>
     fun updateNotification(userId : Int)

}