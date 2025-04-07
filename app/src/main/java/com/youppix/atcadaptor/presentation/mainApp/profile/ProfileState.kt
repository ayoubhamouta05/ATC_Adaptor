package com.youppix.atcadaptor.presentation.mainApp.profile

import com.youppix.atcadaptor.domain.model.user.User

data class ProfileState(
    val user: User = User(),
    val isLoading : Boolean = false ,
    val showDialog : Boolean = false ,
    val isNotificationEnable : Boolean = false ,
    val error : String? = null
)
