package com.arya.githubuser.core.domain.usecase

import com.arya.githubuser.core.domain.repository.SettingRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveThemeSettingUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(isDarkModeActive: Boolean) =
        settingRepository.saveThemeSetting(isDarkModeActive = isDarkModeActive)
}