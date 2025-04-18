package com.youppix.atcadaptor.presentation.mainApp.details

import com.youppix.atcadaptor.domain.model.details.DetailsData


data class DetailsState(
    val isLoading : Boolean = false ,
    val detailsData: DetailsData ?=null,
    val errorMessage : String ? = null
)
