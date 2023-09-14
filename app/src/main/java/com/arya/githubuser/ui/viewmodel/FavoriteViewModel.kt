package com.arya.githubuser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arya.githubuser.database.FavoriteUserRepository
import com.arya.githubuser.model.GithubUser

class FavoriteViewModel : ViewModel() {
    private var mFavoriteUserRepository: FavoriteUserRepository? = null

    fun initRepository(application: Application) {
        mFavoriteUserRepository = FavoriteUserRepository(application)
    }

    fun getAllFavoriteUsers(): LiveData<List<GithubUser>>? {
        return mFavoriteUserRepository?.getAllFavoriteUsers()
    }
}