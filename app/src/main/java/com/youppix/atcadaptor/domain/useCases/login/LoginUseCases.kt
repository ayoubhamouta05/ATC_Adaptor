package com.youppix.atcadaptor.domain.useCases.login

import com.youppix.atcadaptor.domain.useCases.auth.CheckEmailUseCase
import com.youppix.atcadaptor.domain.useCases.auth.CheckPasswordUseCase
import com.youppix.atcadaptor.domain.useCases.appEntry.SaveAppEntryUseCase


data class LoginUseCases(
    val checkEmail: CheckEmailUseCase,
    val checkPassword: CheckPasswordUseCase,
    val login : LoginUseCase,
    val saveAppEntry : SaveAppEntryUseCase?=null
)