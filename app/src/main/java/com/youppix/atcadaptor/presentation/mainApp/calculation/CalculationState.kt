package com.youppix.atcadaptor.presentation.mainApp.calculation

data class CalculationState(
    val isLoading: Boolean = false,
    val error: String? = "",
    val showBottomSheet: Boolean = false,

    val dropGenreMenu: Boolean = false,
    val dropRaceMenu: Boolean = false,
    val dropToxiciteRenaleMenu: Boolean = false,
    val dropToxiciteHepatiqueMenu: Boolean = false,
    val dropDialyseMenu: Boolean = false,
    val dropDfgTypeMenu: Boolean = false,
    val dropNomDeMedicamentMenu: Boolean = false,

    val recommendationAndDose : Pair<String,Double> = Pair("" , 0.0)
)
