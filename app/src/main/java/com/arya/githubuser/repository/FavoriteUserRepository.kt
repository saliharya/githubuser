package com.arya.githubuser.repository

import com.arya.githubuser.database.FavoriteUserDao
import com.arya.githubuser.model.GithubUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteUserRepository @Inject constructor(
    private val mFavoriteUserDao: FavoriteUserDao
) {

    suspend fun getFavoriteUsers(): List<GithubUser> = mFavoriteUserDao.getFavoriteUsers()

    suspend fun insert(githubUser: GithubUser) {
        mFavoriteUserDao.insertFavoriteUser(githubUser)
    }

    suspend fun delete(githubUser: GithubUser) {
        mFavoriteUserDao.deleteFavoriteUser(githubUser)
    }
}
