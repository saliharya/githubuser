package com.arya.githubuser.core.data.repository

import com.arya.githubuser.common.utils.ResourceState
import com.arya.githubuser.common.utils.tryParse
import com.arya.githubuser.core.data.mapper.toEntity
import com.arya.githubuser.core.data.remote.GitHubService
import com.arya.githubuser.core.domain.repository.GithubRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepositoryImpl @Inject constructor(
    private val gitHubService: GitHubService
) : GithubRepository {

    override suspend fun fetchGithubUsers(query: String) = flow {
        emit(ResourceState.Loading())
        val response = gitHubService.searchUsers(query)
        emit(response.tryParse {
            it?.items?.map { item -> item.toEntity() }.orEmpty()
        })
    }

    override suspend fun fetchGithubUserDetail(username: String) = flow {
        emit(ResourceState.Loading())
        val response = gitHubService.getUserDetail(username)
        emit(response.tryParse { it?.toEntity() })
    }

    override suspend fun getFollowers(username: String) = flow {
        emit(ResourceState.Loading())
        val response = gitHubService.getFollowers(username)
        emit(response.tryParse {
            it?.map { item -> item.toEntity() }.orEmpty()
        })
    }

    override suspend fun getFollowings(username: String) = flow {
        emit(ResourceState.Loading())
        val response = gitHubService.getFollowing(username)
        emit(response.tryParse {
            it?.map { item -> item.toEntity() }.orEmpty()
        })
    }
}