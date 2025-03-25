package com.youppix.atcadaptor.presentation.mainApp.scanner

sealed class ScannerScreenEvent {
    data class SaveAppEntry(val value: String) : ScannerScreenEvent()
    data class SwitchScannerScreen(val value: Boolean) : ScannerScreenEvent()
    data object ToggleNeedToBeLoggedBottomSheet : ScannerScreenEvent()
}