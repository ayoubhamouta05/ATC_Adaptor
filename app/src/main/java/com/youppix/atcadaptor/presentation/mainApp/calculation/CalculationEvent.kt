package com.youppix.atcadaptor.presentation.mainApp.calculation

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


}