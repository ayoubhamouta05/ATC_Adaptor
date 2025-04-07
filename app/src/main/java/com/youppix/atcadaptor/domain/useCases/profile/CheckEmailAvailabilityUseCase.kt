package com.youppix.atcadaptor.domain.useCases.profile

import com.youppix.atcadaptor.domain.repository.profile.ProfileRepository

class CheckEmailAvailabilityUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(userId: Int , email : String) =
        profileRepository.checkEmailAvailability(userId , email)

}