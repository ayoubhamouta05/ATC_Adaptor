package com.youppix.atcadaptor.presentation.mainApp.patientDetails

import com.youppix.atcadaptor.domain.model.details.PatientData

data class PatientDetailsState(
    val isLoading : Boolean = false ,
    val error : String ? = null ,
    val patientData: PatientData? = null ,
)
