package com.arya.githubuser.database

import android.app.Application
import androidx.lifecycle.LiveData
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
