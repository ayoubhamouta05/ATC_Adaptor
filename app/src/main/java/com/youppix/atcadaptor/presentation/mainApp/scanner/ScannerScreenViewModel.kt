package com.youppix.atcadaptor.presentation.mainApp.scanner

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import com.youppix.atcadaptor.common.Constant.APP_ENTRY
import com.youppix.atcadaptor.domain.useCases.appEntry.AppEntryUseCases
import io.ktor.util.valuesOf
import javax.inject.Inject

class ScannerScreenViewModel@Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
) : ScreenModel {
    private var _state = mutableStateOf(ScannerState(false))
    val state : State<ScannerState> = _state

    fun onEvent(event: ScannerScreenEvent) {
        when(event){
            is ScannerScreenEvent.SaveAppEntry -> {
                saveAppEntry(value = event.value)
            }

            is ScannerScreenEvent.SwitchScannerScreen -> {
                _state.value = state.value.copy(
                    isScannerScreen = event.value
                )
            }

            ScannerScreenEvent.ToggleNeedToBeLoggedBottomSheet -> {
                _state.value = state.value.copy(
                    showNeedToBeLogged = !state.value.showNeedToBeLogged
                )
            }

        }
    }

    private fun saveAppEntry(value : String){
        appEntryUseCases.saveAppEntryUseCase(APP_ENTRY , value)
    }

}