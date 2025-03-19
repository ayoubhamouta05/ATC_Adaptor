package com.youppix.atcadaptor.domain.repository.home

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.home.dto.HomeSearchResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
   suspend fun search(searchQuery: String , userType : Int): Flow<Resource<HomeSearchResponse>>
}