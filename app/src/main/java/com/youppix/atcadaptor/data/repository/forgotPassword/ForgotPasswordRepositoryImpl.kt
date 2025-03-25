package com.youppix.atcadaptor.data.repository.forgotPassword

import android.content.Context
import com.youppix.atcadaptor.common.Constant
import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.auth.AuthService
import com.youppix.atcadaptor.data.remote.dto.StandardResponse
import com.youppix.atcadaptor.domain.repository.forgotPassword.ForgotPasswordRepository
import kotlinx.coroutines.flow.Flow

class ForgotPasswordRepositoryImpl(
    private val authService: AuthService
): ForgotPasswordRepository {
    override fun checkEmail(email: String , context: Context): Resource<Boolean> {
        return Constant.checkEmail(email ,context)
    }

    override fun checkPassword(password: String , context: Context): Resource<Boolean> {
        return Constant.checkPassword(password, context)
    }

    override suspend fun checkEmail(email: String): Flow<Resource<StandardResponse>> {
        return authService.checkEmail(email)
    }

    override suspend fun verifyCode(
        email: String,
        verifyCode: String
    ): Flow<Resource<StandardResponse>> {
        return authService.verifyCode(email,verifyCode, verifyCodeForgotPassword = true)
    }

    override suspend fun resetPassword(
        email: String,
        password: String
    ): Flow<Resource<StandardResponse>> {
        return authService.resetPassword(email, password)
    }
}