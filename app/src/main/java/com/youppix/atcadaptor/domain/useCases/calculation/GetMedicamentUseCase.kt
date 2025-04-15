package com.youppix.atcadaptor.domain.useCases.calculation

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.calculation.dto.GetMedicamentResponse
import com.youppix.atcadaptor.domain.repository.calculation.CalculationRepository
import kotlinx.coroutines.flow.Flow

data class GetMedicamentUseCase(
    private val calculationRepository : CalculationRepository
){
    operator fun invoke() : Flow<Resource<GetMedicamentResponse>>{
        return calculationRepository.getMedicament()
    }
}
