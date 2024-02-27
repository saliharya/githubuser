package com.arya.githubuser.core.domain.usecase

import com.arya.githubuser.core.domain.repository.GithubRepository

class FetchGithubUserUseCase(private val githubUserRepository: GithubRepository) {
    suspend operator fun invoke(
        query: String
    ) = githubUserRepository.fetchGithubUsers(query)
}