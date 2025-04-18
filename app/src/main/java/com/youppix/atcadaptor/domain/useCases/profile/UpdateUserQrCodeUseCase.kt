package com.youppix.atcadaptor.domain.useCases.profile

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.dto.StandardResponse
import com.youppix.atcadaptor.domain.repository.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow

class UpdateUserQrCodeUseCase(
    private val profileRepository: ProfileRepository
) {
     operator fun invoke( userId : String,imageBase64 : String , ): Flow<Resource<StandardResponse>> =
        profileRepository.updateUserQrCode(userId= userId, imageBase64 = imageBase64)
}