package com.arya.githubuser.core.domain.usecase

import com.arya.githubuser.core.domain.repository.GithubRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchGithubUserUseCase @Inject constructor(
    private val githubUserRepository: GithubRepository
) {
    suspend operator fun invoke(
        query: String
    ) = githubUserRepository.fetchGithubUsers(query)
}