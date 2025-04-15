package com.youppix.atcadaptor.data.repository.calculation

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.calculation.CalculationService
import com.youppix.atcadaptor.data.remote.calculation.dto.GetCalculationByIdResponse
import com.youppix.atcadaptor.data.remote.calculation.dto.GetCalculationsHistoryResponse
import com.youppix.atcadaptor.data.remote.calculation.dto.GetMedicamentResponse
import com.youppix.atcadaptor.data.remote.dto.StandardResponse
import com.youppix.atcadaptor.domain.model.calculations.CalculationHistoryData
import com.youppix.atcadaptor.domain.repository.calculation.CalculationRepository
import kotlinx.coroutines.flow.Flow

class CalculationRepositoryImpl(private val calculationService: CalculationService) :
    CalculationRepository {
    override fun getMedicament(): Flow<Resource<GetMedicamentResponse>> {
        return calculationService.getMedicaments()
    }

    override fun getCalculationsHistory(userId: String): Flow<Resource<GetCalculationsHistoryResponse>> {
        return calculationService.getCalculationHistory(userId)
    }

    override fun getCalculationById(
        userId: String,
        calculationId: String
    ): Flow<Resource<GetCalculationByIdResponse>> {
        return calculationService.getCalculationById(userId = userId, calculationId = calculationId)
    }

    override fun addCalculationHistory(calculationHistoryData: CalculationHistoryData): Flow<Resource<StandardResponse>> {
        return calculationService.saveCalculationHistory(calculationHistoryData)
    }
}