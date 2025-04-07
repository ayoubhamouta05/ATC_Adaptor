package com.youppix.atcadaptor.domain.useCases.profile

import com.youppix.atcadaptor.domain.repository.profile.ProfileRepository

class SaveUserData (
    private val profileRepository: ProfileRepository
) {

    operator fun invoke(key : String , value: String){
        profileRepository.saveAppEntry(key , value)
    }

}