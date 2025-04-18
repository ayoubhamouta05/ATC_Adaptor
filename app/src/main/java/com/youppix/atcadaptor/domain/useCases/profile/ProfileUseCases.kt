package com.youppix.atcadaptor.domain.useCases.profile


import com.youppix.atcadaptor.domain.useCases.auth.CheckEmailUseCase
import com.youppix.atcadaptor.domain.useCases.auth.CheckPasswordUseCase
import com.youppix.atcadaptor.domain.useCases.signUp.CheckPhoneUseCase
import com.youppix.atcadaptor.domain.useCases.signUp.CheckUserNameUseCase


data class ProfileUseCases (
    val saveUserData : SaveUserData,
    val getUserData : GetUserDataUseCase,
    val updatePersonalDetails : UpdatePersonalDetailsUseCase,
    val checkEmail : CheckEmailUseCase,
    val checkPassword: CheckPasswordUseCase,
    val checkUserName: CheckUserNameUseCase,
    val checkPhone : CheckPhoneUseCase,
    val checkEmailAvailability : CheckEmailAvailabilityUseCase ,
    val updateUserQrCode : UpdateUserQrCodeUseCase
)