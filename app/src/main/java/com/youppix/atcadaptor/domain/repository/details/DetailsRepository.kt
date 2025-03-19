package com.youppix.atcadaptor.domain.repository.details

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.details.dto.DetailsResponse
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    suspend fun getDetails (medicationName : String ,patientId : Int ?= null ) : Flow<Resource<DetailsResponse>>

}