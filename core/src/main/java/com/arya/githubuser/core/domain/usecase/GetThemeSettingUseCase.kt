package com.arya.githubuser.core.domain.usecase

import com.arya.githubuser.core.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetThemeSettingUseCase : KoinComponent {
    private val settingRepository: SettingRepository by inject()

    operator fun invoke(): Flow<Boolean> = settingRepository.getThemeSetting()
}