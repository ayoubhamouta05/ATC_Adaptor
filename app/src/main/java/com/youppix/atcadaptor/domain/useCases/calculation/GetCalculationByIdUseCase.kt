package com.youppix.atcadaptor.domain.useCases.calculation

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.calculation.dto.GetCalculationByIdResponse
import com.youppix.atcadaptor.data.remote.calculation.dto.GetMedicamentResponse
import com.youppix.atcadaptor.domain.repository.calculation.CalculationRepository
import kotlinx.coroutines.flow.Flow

class GetCalculationByIdUseCase(
    private val calculationRepository : CalculationRepository
){
    operator fun invoke(userId : String , calculationId : String) : Flow<Resource<GetCalculationByIdResponse>> {
        return calculationRepository.getCalculationById(userId , calculationId)
    }
}