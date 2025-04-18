package com.youppix.atcadaptor.presentation.mainApp.calculationsHistory

import com.youppix.atcadaptor.domain.model.calculations.CalculationsHistory

data class CalculationsHistoryState(
    val isLoading : Boolean = false ,
    val error : String ? = null ,
    val historyList : List<CalculationsHistory> = emptyList()
)
