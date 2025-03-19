package com.youppix.atcadaptor.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import com.youppix.atcadaptor.presentation.authentication.login.LoginViewModel
import com.youppix.atcadaptor.presentation.mainApp.calculation.CalculationViewModel
import com.youppix.atcadaptor.presentation.mainApp.details.DetailsViewModel
import com.youppix.atcadaptor.presentation.mainApp.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityComponent::class)
abstract class HiltScreenModels {

    @Binds
    @IntoMap
    @ScreenModelKey(LoginViewModel::class)
    abstract fun bindHiltLoginViewModel(loginViewModel: LoginViewModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(HomeViewModel::class)
    abstract fun bindHiltHomeViewModel(homeViewModel: HomeViewModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(DetailsViewModel::class)
    abstract fun bindHiltDetailsViewModel(detailsViewModel: DetailsViewModel): ScreenModel


    @Binds
    @IntoMap
    @ScreenModelKey(CalculationViewModel::class)
    abstract fun bindHiltCalculationViewModel(calculationViewModel: CalculationViewModel): ScreenModel


}