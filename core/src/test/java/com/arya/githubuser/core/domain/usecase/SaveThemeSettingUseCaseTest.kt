package com.arya.githubuser.core.domain.usecase

import com.arya.githubuser.core.domain.repository.SettingRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SaveThemeSettingUseCaseTest : KoinTest {

    @Mock
    private lateinit var settingRepository: SettingRepository

    private lateinit var saveThemeSettingUseCase: SaveThemeSettingUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        saveThemeSettingUseCase = SaveThemeSettingUseCase(settingRepository)
    }

    @Test
    fun saveThemeSetting() = runBlocking {
        val isDarkModeActive = true

        saveThemeSettingUseCase(isDarkModeActive)

        verify(settingRepository).saveThemeSetting(isDarkModeActive)
    }
}
