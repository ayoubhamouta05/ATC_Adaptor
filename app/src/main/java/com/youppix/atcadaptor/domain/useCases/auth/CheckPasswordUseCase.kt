package com.youppix.atcadaptor.domain.useCases.auth

import android.content.Context
import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.domain.repository.forgotPassword.ForgotPasswordRepository
import com.youppix.atcadaptor.domain.repository.login.LoginRepository
import com.youppix.atcadaptor.domain.repository.profile.ProfileRepository
import com.youppix.atcadaptor.domain.repository.signUp.SignUpRepository

class CheckPasswordUseCase(
    private val loginRepository: LoginRepository? = null,
    private val forgotPasswordRepository: ForgotPasswordRepository? = null,
    private val signUpRepository: SignUpRepository? = null,
    private val profileRepository: ProfileRepository? = null
) {

    operator fun invoke(password: String ,context: Context): Resource<Boolean> {
        return loginRepository?.checkPassword(password , context)
            ?: forgotPasswordRepository?.checkPassword(password, context)
            ?: signUpRepository?.checkPassword(password, context)
            ?: profileRepository?.checkPassword(password, context)
            ?: Resource.Error("")
    }

}