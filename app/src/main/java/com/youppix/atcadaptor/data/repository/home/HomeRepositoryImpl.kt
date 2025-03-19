package com.youppix.atcadaptor.data.repository.home

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.auth.AuthService
import com.youppix.atcadaptor.data.remote.home.HomeService
import com.youppix.atcadaptor.data.remote.home.dto.HomeSearchResponse
import com.youppix.atcadaptor.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl(
    private val homeService: HomeService
) : HomeRepository {
    override suspend fun search(searchQuery: String , userType : Int): Flow<Resource<HomeSearchResponse>> {
        return homeService.search(searchQuery, userType)
    }

}