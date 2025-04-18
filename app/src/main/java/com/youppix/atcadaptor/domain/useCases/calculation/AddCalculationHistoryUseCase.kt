package com.youppix.atcadaptor.domain.useCases.calculation

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.dto.StandardResponse
import com.youppix.atcadaptor.domain.model.calculations.CalculationData
import com.youppix.atcadaptor.domain.repository.calculation.CalculationRepository
import kotlinx.coroutines.flow.Flow

class AddCalculationHistoryUseCase ( private val calculationRepository : CalculationRepository
){
    operator fun invoke(calculationData: CalculationData ) : Flow<Resource<StandardResponse>> {
        return calculationRepository.addCalculationHistory(calculationData)
    }
}