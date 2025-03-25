package com.youppix.atcadaptor.data.repository.signUp

import android.content.Context
import com.youppix.atcadaptor.common.Constant
import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.auth.AuthService
import com.youppix.atcadaptor.data.remote.dto.StandardResponse
import com.youppix.atcadaptor.domain.repository.signUp.SignUpRepository

import kotlinx.coroutines.flow.Flow

class SignUpRepositoryImpl(
    private val authService : AuthService
) : SignUpRepository {
    override fun checkUserName(userName: String, context: Context): Resource<Boolean> {
        return Constant.checkUserName(userName , context)
    }

    override fun checkEmail(email: String, context: Context): Resource<Boolean> {
        return Constant.checkEmail(email , context)
    }

    override fun checkPhone(phone: String, context: Context): Resource<Boolean> {
       return Constant.checkPhone(phone , context)
    }

    override fun checkPassword(password: String, context: Context): Resource<Boolean> {
        return Constant.checkPassword(password , context)
    }


    override suspend fun addUser(name : String , email : String , phone : String , password : String,userType : Int): Flow<Resource<StandardResponse>> {
        return authService.addUser(name  , email  , phone , password , userType  )
    }

    override suspend fun verifyCode(email:String , verifyCode: String): Flow<Resource<StandardResponse>> {
        return authService.verifyCode(email, verifyCode)
    }
}