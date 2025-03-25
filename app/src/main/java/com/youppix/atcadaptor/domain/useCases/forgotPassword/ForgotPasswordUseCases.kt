package com.youppix.atcadaptor.domain.useCases.forgotPassword

import com.youppix.atcadaptor.domain.useCases.auth.CheckPasswordUseCase


data class ForgotPasswordUseCases(
    val checkEmail: com.youppix.atcadaptor.domain.useCases.auth.CheckEmailUseCase,
    val checkPassword: CheckPasswordUseCase,
    val resetPassword : ResetPasswordUseCase,
    val checkEmailDb: CheckEmailUseCase,
    val verifyCode : VerifyCodeUseCase
)
