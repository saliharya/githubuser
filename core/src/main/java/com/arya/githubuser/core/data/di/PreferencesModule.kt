package com.arya.githubuser.core.data.di

import com.arya.githubuser.core.data.local.datastore.SettingPreferences
import com.arya.githubuser.core.domain.usecase.GetThemeSettingUseCase
import com.arya.githubuser.core.domain.usecase.SaveThemeSettingUseCase
import org.koin.dsl.module

val preferencesModule = module {
    single { SettingPreferences(get()) }
    single { GetThemeSettingUseCase(get()) }
    single { SaveThemeSettingUseCase(get()) }
}