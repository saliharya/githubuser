package com.arya.githubuser.core.domain.repository

import com.arya.githubuser.common.utils.ResourceState
import com.arya.githubuser.core.domain.model.GithubUserEntity
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun fetchGithubUsers(query: String): Flow<ResourceState<List<GithubUserEntity>>>
    suspend fun fetchGithubUserDetail(username: String): Flow<ResourceState<GithubUserEntity>>
    suspend fun getFollowers(username: String): Flow<ResourceState<List<GithubUserEntity>>>
    suspend fun getFollowings(username: String): Flow<ResourceState<List<GithubUserEntity>>>
}