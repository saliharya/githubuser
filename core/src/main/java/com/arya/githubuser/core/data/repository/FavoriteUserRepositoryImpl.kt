package com.arya.githubuser.core.data.repository

import com.arya.githubuser.core.data.local.database.FavoriteUserDao
import com.arya.githubuser.core.data.mapper.toDto
import com.arya.githubuser.core.data.mapper.toEntity
import com.arya.githubuser.core.domain.model.GithubUserEntity
import com.arya.githubuser.core.domain.repository.FavoriteUserRepository

class FavoriteUserRepositoryImpl(private val mFavoriteUserDao: FavoriteUserDao) :
    FavoriteUserRepository {

    override suspend fun getFavoriteUsers(): List<GithubUserEntity> =
        mFavoriteUserDao.getFavoriteUsers().map { it.toEntity() }

    override suspend fun insert(githubUser: GithubUserEntity) {
        mFavoriteUserDao.insertFavoriteUser(githubUser.toDto())
    }

    override suspend fun delete(githubUser: GithubUserEntity) {
        mFavoriteUserDao.deleteFavoriteUser(githubUser.toDto())
    }
}
