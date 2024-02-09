package com.arya.githubuser.core.domain.repository

import com.arya.githubuser.core.domain.model.GithubUserEntity

interface FavoriteUserRepository {
    suspend fun getFavoriteUsers(): List<GithubUserEntity>
    suspend fun insert(githubUser: GithubUserEntity)
    suspend fun delete(githubUser: GithubUserEntity)
}
