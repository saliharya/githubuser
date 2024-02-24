package com.arya.githubuser.core.domain.usecase

import com.arya.githubuser.core.domain.repository.GithubRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetFollowingUseCase : KoinComponent {
    private val githubUserRepository: GithubRepository by inject()
    suspend operator fun invoke(
        username: String
    ) = githubUserRepository.getFollowings(username)
}