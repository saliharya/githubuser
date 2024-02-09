package com.arya.githubuser.core.data.repository

import com.arya.githubuser.core.data.local.datastore.SettingPreferences
import com.arya.githubuser.core.domain.repository.SettingRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepositoryImpl @Inject constructor(
    private val settingPreferences: SettingPreferences
) : SettingRepository {
    override fun getThemeSetting() = settingPreferences.getThemeSetting()

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) =
        settingPreferences.saveThemeSetting(isDarkModeActive)
}