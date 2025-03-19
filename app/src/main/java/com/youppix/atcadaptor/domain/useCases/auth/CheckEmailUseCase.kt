package com.youppix.atcadaptor.domain.useCases.auth

import android.content.Context
import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.domain.repository.forgotPassword.ForgotPasswordRepository
import com.youppix.atcadaptor.domain.repository.login.LoginRepository
import com.youppix.atcadaptor.domain.repository.profile.ProfileRepository
import com.youppix.atcadaptor.domain.repository.signUp.SignUpRepository

class CheckEmailUseCase(
    private val loginRepository: LoginRepository? = null,
    private val forgotPasswordRepository: ForgotPasswordRepository? = null,
    private val signUpRepository: SignUpRepository? = null,
    private val profileRepository: ProfileRepository? = null,
) {

    operator fun invoke(email: String, context: Context): Resource<Boolean> {
        return loginRepository?.checkEmail(email, context)
            ?: forgotPasswordRepository?.checkEmail(email, context)
            ?: signUpRepository?.checkEmail(email, context)
            ?: profileRepository?.checkEmail(email, context)
            ?: Resource.Error("")

    }

}