package com.youppix.atcadaptor.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId : Int = 0 ,
    val userCustomerId : String? = null,
    val userName : String= "",
    val userEmail : String ="",
    val userPhone : String ="",
    val userImage : String ? = null ,
    val userType : Int = 0
) : java.io.Serializable
