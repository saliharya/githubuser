package com.arya.githubuser.core.domain.usecase

import com.arya.githubuser.core.domain.model.GithubUserEntity
import com.arya.githubuser.core.domain.repository.FavoriteUserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetFavoriteUsersUseCase : KoinComponent {
    private val favoriteUserRepository: FavoriteUserRepository by inject()

    suspend operator fun invoke(): List<GithubUserEntity> =
        favoriteUserRepository.getFavoriteUsers()
}