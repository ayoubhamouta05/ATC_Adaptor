package com.youppix.atcadaptor.domain.useCases.profile

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.dto.StandardResponse
import com.youppix.atcadaptor.domain.repository.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow

class UpdatePersonalDetailsUseCase(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(
        userId: Int,
        name: String,
        email: String,
        phone: String,
        oldPassword: String,
        newPassword: String,
        userCustomerId: String
    ): Flow<Resource<StandardResponse>> = profileRepository.updatePersonalDetails(
        userId = userId,
        name = name,
        email = email,
        phone = phone,
        oldPassword = oldPassword,
        newPassword = newPassword,
        userCustomerId = userCustomerId
    )

}