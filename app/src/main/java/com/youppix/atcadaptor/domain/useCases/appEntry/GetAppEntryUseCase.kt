package com.youppix.atcadaptor.domain.useCases.appEntry

import com.youppix.atcadaptor.domain.manager.LocaleUserEntryManager

class GetAppEntryUseCase(
    private val localeUserEntryManager: LocaleUserEntryManager
) {
    operator fun invoke(key: String, defaultValue: String): String {
        return localeUserEntryManager.readAppEntry(key, defaultValue)
    }
}