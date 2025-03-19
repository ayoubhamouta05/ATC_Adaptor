package com.youppix.atcadaptor.domain.repository.login

import android.content.Context
import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.auth.dto.LoginResponse

import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun checkEmail (email : String , context: Context) : Resource<Boolean>//Flow<Resource<Boolean>>
    fun checkPassword(password : String,context: Context) : Resource<Boolean>// Flow<Resource<Boolean>>
    suspend fun login (email : String, password : String) : Flow<Resource<LoginResponse>>
}