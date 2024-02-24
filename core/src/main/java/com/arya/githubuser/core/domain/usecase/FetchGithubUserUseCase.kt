package com.arya.githubuser.core.domain.usecase

import com.arya.githubuser.core.domain.repository.GithubRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FetchGithubUserUseCase : KoinComponent {
    private val githubUserRepository: GithubRepository by inject()

    suspend operator fun invoke(
        query: String
    ) = githubUserRepository.fetchGithubUsers(query)
}