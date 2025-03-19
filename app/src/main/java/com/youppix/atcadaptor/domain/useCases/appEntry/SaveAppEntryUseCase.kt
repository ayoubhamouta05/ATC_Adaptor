package com.youppix.atcadaptor.domain.useCases.appEntry

import com.youppix.atcadaptor.domain.manager.LocaleUserEntryManager

class SaveAppEntryUseCase(
    private val localeUserEntryManager: LocaleUserEntryManager
) {
    operator fun invoke(key: String, value: String) {
        return localeUserEntryManager.saveAppEntry(key, value)
    }
}