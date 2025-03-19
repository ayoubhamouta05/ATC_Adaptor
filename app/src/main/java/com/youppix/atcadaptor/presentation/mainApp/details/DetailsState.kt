package com.youppix.atcadaptor.presentation.mainApp.details

import com.youppix.atcadaptor.domain.model.details.DetailsData
import com.youppix.atcadaptor.domain.model.details.Patient
import com.youppix.atcadaptor.domain.model.details.Treatment

data class DetailsState(
    val isLoading : Boolean = false ,
    val detailsData: DetailsData ?=null,
    val errorMessage : String ? = null
)
