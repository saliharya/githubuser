package com.arya.githubuser.core.domain.usecase

import com.arya.githubuser.core.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow

class GetThemeSettingUseCase(private val settingRepository: SettingRepository) {
    operator fun invoke(): Flow<Boolean> = settingRepository.getThemeSetting()
}