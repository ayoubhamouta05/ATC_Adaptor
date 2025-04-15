package com.youppix.atcadaptor.presentation.mainApp.calculation

import android.content.Context
import com.youppix.atcadaptor.domain.model.calculations.CalculationHistoryData

sealed class CalculationEvent {
    data object OnCalculate : CalculationEvent()
    data object InitializeErrorMessage : CalculationEvent()
    data object ToggleGenreDropMenu : CalculationEvent()
    data object ToggleRaceDropMenu : CalculationEvent()
    data object ToggleToxiciteRenaleDropMenu : CalculationEvent()
    data object ToggleToxiciteHepatiqueDropMenu : CalculationEvent()
    data object ToggleDialyseDropMenu : CalculationEvent()
    data object ToggleDfgTypeDropMenu : CalculationEvent()
    data object ToggleShowBottomSheet : CalculationEvent()
    data object ToggleNomDeMedicament : CalculationEvent()

    data object GetMedicament : CalculationEvent()

    data class  OnCommentValueChange (val value : String) : CalculationEvent()
    data class  SaveCalculation (val context : Context , val calculationHistoryData: CalculationHistoryData) : CalculationEvent()
    data class  ShowToast (val context: Context,val message: String ) : CalculationEvent()


}