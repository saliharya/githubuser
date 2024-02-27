package com.arya.githubuser.core.domain.usecase

import com.arya.githubuser.core.domain.repository.GithubRepository

class GetFollowerUseCase(private val githubUserRepository: GithubRepository) {
    suspend operator fun invoke(
        username: String
    ) = githubUserRepository.getFollowers(username)
}