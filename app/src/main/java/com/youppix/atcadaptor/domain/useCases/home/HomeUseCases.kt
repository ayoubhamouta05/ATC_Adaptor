package com.youppix.atcadaptor.domain.useCases.home

import com.youppix.atcadaptor.domain.useCases.appEntry.SaveAppEntryUseCase

data class HomeUseCases(
    val search : HomeSearchUseCase,
    val saveAppEntry : SaveAppEntryUseCase?=null,
)
