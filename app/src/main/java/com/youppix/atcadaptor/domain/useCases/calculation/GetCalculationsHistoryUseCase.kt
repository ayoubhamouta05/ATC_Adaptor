package com.youppix.atcadaptor.domain.useCases.calculation

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.calculation.dto.GetCalculationsHistoryResponse
import com.youppix.atcadaptor.domain.repository.calculation.CalculationRepository
import kotlinx.coroutines.flow.Flow

class GetCalculationsHistoryUseCase(
    private val calculationRepository : CalculationRepository
){
    operator fun invoke(userId :String ) : Flow<Resource<GetCalculationsHistoryResponse>> {
        return calculationRepository.getCalculationsHistory(userId)
    }
}