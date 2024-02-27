package com.arya.githubuser.core.domain.usecase

import com.arya.githubuser.core.domain.repository.SettingRepository

class SaveThemeSettingUseCase(private val settingRepository: SettingRepository) {
    suspend operator fun invoke(isDarkModeActive: Boolean) =
        settingRepository.saveThemeSetting(isDarkModeActive = isDarkModeActive)
}