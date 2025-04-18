package com.youppix.atcadaptor.domain.useCases.calculation

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.calculation.dto.GetUsersResponse
import com.youppix.atcadaptor.domain.repository.calculation.CalculationRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(
    private val calculationRepository: CalculationRepository
) {
    operator fun invoke(searchQuery: String): Flow<Resource<GetUsersResponse>> {
        return calculationRepository.getUsers(searchQuery)
    }
}