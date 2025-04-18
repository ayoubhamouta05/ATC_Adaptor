package com.youppix.atcadaptor.domain.repository.details

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.details.dto.DetailsResponse
import com.youppix.atcadaptor.data.remote.details.dto.PatientDataResponse
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    suspend fun getDetails (medicationName : String ,patientId : Int ?= null ) : Flow<Resource<DetailsResponse>>
    fun getPatientData (patientId : Int ?= null, name : String , email : String , phone : String ) : Flow<Resource<PatientDataResponse>>

}