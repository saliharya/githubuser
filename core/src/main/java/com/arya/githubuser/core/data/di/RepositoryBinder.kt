package com.arya.githubuser.core.data.di

import com.arya.githubuser.core.data.repository.FavoriteUserRepositoryImpl
import com.arya.githubuser.core.data.repository.GithubRepositoryImpl
import com.arya.githubuser.core.data.repository.SettingRepositoryImpl
import com.arya.githubuser.core.domain.repository.FavoriteUserRepository
import com.arya.githubuser.core.domain.repository.GithubRepository
import com.arya.githubuser.core.domain.repository.SettingRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<GithubRepository> { GithubRepositoryImpl() }
    single<FavoriteUserRepository> { FavoriteUserRepositoryImpl() }
    single<SettingRepository> { SettingRepositoryImpl() }
}
