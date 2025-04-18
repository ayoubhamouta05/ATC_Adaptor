package com.youppix.atcadaptor.presentation.mainApp.scanner

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.youppix.atcadaptor.common.Constant.APP_ENTRY
import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.domain.useCases.appEntry.AppEntryUseCases
import com.youppix.atcadaptor.domain.useCases.details.DetailsUseCases
import com.youppix.atcadaptor.domain.useCases.profile.ProfileUseCases
import io.ktor.util.valuesOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ScannerScreenViewModel@Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
    private val detailsUseCases: DetailsUseCases
) : ScreenModel {
    private var _state = mutableStateOf(ScannerState(false))
    val state : State<ScannerState> = _state

    fun onEvent(event: ScannerScreenEvent) {
        when(event){
            is ScannerScreenEvent.SaveAppEntry -> {
                saveAppEntry(value = event.value)
            }

            ScannerScreenEvent.ToggleNeedToBeLoggedBottomSheet -> {
                _state.value = state.value.copy(
                    showNeedToBeLogged = !state.value.showNeedToBeLogged
                )
            }

            is ScannerScreenEvent.GetPatientData -> {
                getUserData(event.name , event.email, event.phone)
            }
        }
    }

    private fun saveAppEntry(value : String){
        appEntryUseCases.saveAppEntryUseCase(APP_ENTRY , value)
    }

    private fun getUserData (name : String , email : String , phone : String ){
        detailsUseCases.getPatientDataUseCase(null , name , email , phone).onEach { result->
            when(result){
                is Resource.Loading ->{
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false ,
                        patientData = null,
                        error = result.message?: result.data?.message?: "An Unexpected Error"
                    )
                }

                is Resource.Successful -> {
                    _state.value = state.value.copy(
                        isLoading = false ,
                        patientData = result.data?.patientData ,
                        error = null
                    )
                }
            }



        }.launchIn(screenModelScope)
    }

}