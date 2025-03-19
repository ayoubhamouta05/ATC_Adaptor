package com.youppix.atcadaptor.data.repository.login

import android.content.Context
import com.youppix.atcadaptor.common.Constant
import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.auth.dto.LoginResponse
import com.youppix.atcadaptor.domain.repository.login.LoginRepository
import com.youppix.atcadaptor.data.remote.auth.AuthService
import kotlinx.coroutines.flow.Flow

class LoginRepositoryImpl(
    private val authService: AuthService
) : LoginRepository {

    override fun checkEmail(email: String, context: Context): Resource<Boolean> {
        return Constant.checkEmail(email, context)
    }

    override fun checkPassword(password: String, context: Context): Resource<Boolean> {
        return Constant.checkPassword(password, context)
    }

    override suspend fun login(email: String, password: String): Flow<Resource<LoginResponse>> {
        return authService.login(email, password)
    }
}