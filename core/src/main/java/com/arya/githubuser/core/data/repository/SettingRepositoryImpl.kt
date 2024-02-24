package com.arya.githubuser.core.data.repository

import com.arya.githubuser.core.data.local.datastore.SettingPreferences
import com.arya.githubuser.core.domain.repository.SettingRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingRepositoryImpl : SettingRepository, KoinComponent {

    private val settingPreferences: SettingPreferences by inject()

    override fun getThemeSetting() = settingPreferences.getThemeSetting()

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) =
        settingPreferences.saveThemeSetting(isDarkModeActive)
}
