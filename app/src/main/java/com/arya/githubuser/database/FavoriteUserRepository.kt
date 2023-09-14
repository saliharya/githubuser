package com.arya.githubuser.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.arya.githubuser.model.GithubUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<GithubUser>> = mFavoriteUserDao.getAllFavoriteUser()

    fun insert(githubUser: GithubUser) {
        executorService.execute { mFavoriteUserDao.insert(githubUser) }
    }

    fun delete(githubUser: GithubUser) {
        executorService.execute { mFavoriteUserDao.deleteById(githubUser.id ?: -1) }
    }
}