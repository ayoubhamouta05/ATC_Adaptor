package com.youppix.atcadaptor.presentation.mainApp.scanner

import com.youppix.atcadaptor.domain.model.details.PatientData

data class ScannerState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val patientData: PatientData? = null,
    val showNeedToBeLogged: Boolean = false,
)
