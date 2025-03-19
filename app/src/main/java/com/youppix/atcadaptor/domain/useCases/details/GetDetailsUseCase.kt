package com.youppix.atcadaptor.domain.useCases.details

import com.youppix.atcadaptor.domain.repository.details.DetailsRepository

class GetDetailsUseCase(
    private val detailsRepository: DetailsRepository
) {
    suspend operator fun invoke(medicationName: String, patientId: Int? = null) =
        detailsRepository.getDetails(medicationName, patientId)

}