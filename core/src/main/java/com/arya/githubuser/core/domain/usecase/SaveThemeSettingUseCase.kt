package com.arya.githubuser.core.domain.usecase

import com.arya.githubuser.core.domain.repository.SettingRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SaveThemeSettingUseCase : KoinComponent {
    private val settingRepository: SettingRepository by inject()

    suspend operator fun invoke(isDarkModeActive: Boolean) =
        settingRepository.saveThemeSetting(isDarkModeActive = isDarkModeActive)
}