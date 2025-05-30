package com.youppix.atcadaptor.data.repository.details

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.details.DetailsService
import com.youppix.atcadaptor.data.remote.details.dto.DetailsResponse
import com.youppix.atcadaptor.data.remote.details.dto.PatientDataResponse
import com.youppix.atcadaptor.domain.repository.details.DetailsRepository
import kotlinx.coroutines.flow.Flow

class DetailsRepositoryImpl(private val detailsService: DetailsService) : DetailsRepository {
    override suspend fun getDetails(
        medicationName: String,
        patientId: Int?,
    ): Flow<Resource<DetailsResponse>> {
        return detailsService.getDetails(medicationName, patientId)
    }

    override fun getPatientData(
        patientId: Int?,
        name: String,
        email: String,
        phone: String
    ): Flow<Resource<PatientDataResponse>> {
        return detailsService.getPatientData(
            patientId = patientId,
            name = name,
            email = email,
            phone = phone
        )
    }
}