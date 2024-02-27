package com.arya.githubuser.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun getThemeSetting(): Flow<Boolean>
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
}