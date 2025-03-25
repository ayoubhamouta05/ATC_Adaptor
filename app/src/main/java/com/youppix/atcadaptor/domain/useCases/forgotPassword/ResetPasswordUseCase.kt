package com.youppix.atcadaptor.domain.useCases.forgotPassword

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.dto.StandardResponse
import com.youppix.atcadaptor.domain.repository.forgotPassword.ForgotPasswordRepository
import kotlinx.coroutines.flow.Flow

class ResetPasswordUseCase(
    private val forgotPasswordRepository: ForgotPasswordRepository
) {

    suspend operator fun invoke(email: String , password : String) : Flow<Resource<StandardResponse>> {
        return forgotPasswordRepository.resetPassword(email , password)
    }

}