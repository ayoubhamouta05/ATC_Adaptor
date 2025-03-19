package com.youppix.atcadaptor.presentation.mainApp.home

import androidx.compose.runtime.Immutable

@Immutable
sealed class HomeEvent {
    @Immutable
    data class Search (val value : String) : HomeEvent()

    @Immutable
    data class UpdateSearchQuery(val value: String) : HomeEvent()

    @Immutable
    data class UpdateUserType(val value: Int) : HomeEvent()

}