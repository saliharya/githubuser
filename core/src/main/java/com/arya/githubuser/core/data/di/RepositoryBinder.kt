package com.arya.githubuser.core.data.di

import com.arya.githubuser.core.data.repository.FavoriteUserRepositoryImpl
import com.arya.githubuser.core.data.repository.GithubRepositoryImpl
import com.arya.githubuser.core.data.repository.SettingRepositoryImpl
import com.arya.githubuser.core.domain.repository.FavoriteUserRepository
import com.arya.githubuser.core.domain.repository.GithubRepository
import com.arya.githubuser.core.domain.repository.SettingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBinder {
    @Binds
    fun bindGithubRepositoryImpl(
        githubRepositoryImpl: GithubRepositoryImpl
    ): GithubRepository

    @Binds
    fun bindFavoriteUserRepositoryImpl(
        favoriteUserRepositoryImpl: FavoriteUserRepositoryImpl
    ): FavoriteUserRepository

    @Binds
    fun bindSettingRepositoryImpl(
        settingRepositoryImpl: SettingRepositoryImpl
    ): SettingRepository
}