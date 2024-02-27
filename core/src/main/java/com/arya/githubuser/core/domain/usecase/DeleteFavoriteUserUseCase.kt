package com.arya.githubuser.core.domain.usecase

import com.arya.githubuser.core.domain.model.GithubUserEntity
import com.arya.githubuser.core.domain.repository.FavoriteUserRepository

class DeleteFavoriteUserUseCase(private val favoriteUserRepository: FavoriteUserRepository) {

    suspend operator fun invoke(githubUserEntity: GithubUserEntity) =
        favoriteUserRepository.delete(githubUserEntity)
}
