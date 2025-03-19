package com.youppix.atcadaptor.domain.repository.forgotPassword

import android.content.Context
import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.dto.StandardResponse

import kotlinx.coroutines.flow.Flow

interface ForgotPasswordRepository {

    fun checkEmail(email : String , context: Context) : Resource<Boolean>

    fun checkPassword(password : String,context: Context ) : Resource<Boolean>

    suspend fun checkEmail(email:String) : Flow<Resource<StandardResponse>>

    suspend fun verifyCode(email  :String , verifyCode: String) : Flow<Resource<StandardResponse>>

    suspend fun resetPassword(email : String, password : String) : Flow<Resource<StandardResponse>>
}