package com.youppix.atcadaptor.domain.useCases.login

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.auth.dto.LoginResponse
import com.youppix.atcadaptor.domain.repository.login.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email : String , password : String) : Flow<Resource<LoginResponse>>{
        return loginRepository.login(email , password)
    }
}