package com.youppix.atcadaptor.domain.useCases.home

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.home.dto.HomeSearchResponse
import com.youppix.atcadaptor.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeSearchUseCase(
    private val homeRepository: HomeRepository,
) {
    suspend operator fun invoke(
        searchQuery: String,
        userType: Int,
    ): Flow<Resource<HomeSearchResponse>> {
        return homeRepository.search(searchQuery, userType)
    }
}