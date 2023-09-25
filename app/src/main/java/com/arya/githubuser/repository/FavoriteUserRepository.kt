package com.arya.githubuser.repository

import android.app.Application
import com.arya.githubuser.database.FavoriteUserDao
import com.arya.githubuser.database.FavoriteUserRoomDatabase
import com.arya.githubuser.model.GithubUser

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    suspend fun getAllFavoriteUsers(): List<GithubUser> = mFavoriteUserDao.getFavoriteUsers()

    suspend fun insert(githubUser: GithubUser) {
        mFavoriteUserDao.insertFavoriteUser(githubUser)
    }

    suspend fun delete(githubUser: GithubUser) {
        mFavoriteUserDao.deleteFavoriteUser(githubUser)
    }
}
