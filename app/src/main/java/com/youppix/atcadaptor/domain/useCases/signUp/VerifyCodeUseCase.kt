package com.youppix.atcadaptor.domain.useCases.signUp

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.dto.StandardResponse
import com.youppix.atcadaptor.domain.repository.signUp.SignUpRepository
import kotlinx.coroutines.flow.Flow

class VerifyCodeUseCase(
    private val signUpRepository: SignUpRepository
) {
    suspend operator fun invoke(email : String , verifyCode: String) : Flow<Resource<StandardResponse>> {
        return signUpRepository.verifyCode(email, verifyCode)
    }
}