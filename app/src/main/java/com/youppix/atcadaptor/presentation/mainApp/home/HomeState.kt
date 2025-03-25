package com.youppix.atcadaptor.presentation.mainApp.home

import com.youppix.atcadaptor.domain.model.home.HomeSearchItemData

data class HomeState(
    val isLoading : Boolean,
    val needToBeLogged : Boolean = false,
    val userType : Int = 0,
    val errorMessage : String? = null,
    val items : List<HomeSearchItemData> = emptyList(),


    )
