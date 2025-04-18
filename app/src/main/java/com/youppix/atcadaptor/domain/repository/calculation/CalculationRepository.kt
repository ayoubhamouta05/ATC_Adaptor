package com.youppix.atcadaptor.domain.repository.calculation

import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.data.remote.calculation.dto.GetCalculationByIdResponse
import com.youppix.atcadaptor.data.remote.calculation.dto.GetCalculationsHistoryResponse
import com.youppix.atcadaptor.data.remote.calculation.dto.GetMedicamentResponse
import com.youppix.atcadaptor.data.remote.calculation.dto.GetUsersResponse
import com.youppix.atcadaptor.data.remote.dto.StandardResponse
import com.youppix.atcadaptor.domain.model.calculations.CalculationData
import kotlinx.coroutines.flow.Flow

interface CalculationRepository {

    fun getMedicament(): Flow<Resource<GetMedicamentResponse>>
    fun getUsers(searchQuery : String ): Flow<Resource<GetUsersResponse>>
    fun getCalculationsHistory(userId : String): Flow<Resource<GetCalculationsHistoryResponse>>
    fun getCalculationById(userId : String , calculationId : String ): Flow<Resource<GetCalculationByIdResponse>>
    fun addCalculationHistory(calculationData: CalculationData): Flow<Resource<StandardResponse>>

}