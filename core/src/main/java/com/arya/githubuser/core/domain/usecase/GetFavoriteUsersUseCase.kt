package com.arya.githubuser.core.domain.usecase

import com.arya.githubuser.core.domain.model.GithubUserEntity
import com.arya.githubuser.core.domain.repository.FavoriteUserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFavoriteUsersUseCase @Inject constructor(
    private val favoriteUserRepository: FavoriteUserRepository
) {
    suspend operator fun invoke(): List<GithubUserEntity> =
        favoriteUserRepository.getFavoriteUsers()
}