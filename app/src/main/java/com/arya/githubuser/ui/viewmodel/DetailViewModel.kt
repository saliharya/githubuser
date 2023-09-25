package com.arya.githubuser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arya.githubuser.api.GitHubService
import com.arya.githubuser.database.FavoriteUserRepository
import com.arya.githubuser.model.GithubUser
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val apiKey = "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"
    private val gitHubService = GitHubService.create(apiKey)

    private var mFavoriteUserRepository: FavoriteUserRepository? = null

    private val _responseLiveData: MutableLiveData<GithubUser> = MutableLiveData()
    val responseLiveData: LiveData<GithubUser> = _responseLiveData

    var favoriteUsersLiveData: LiveData<List<GithubUser>>? = null

    private val _errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val errorLiveData: LiveData<Throwable> = _errorLiveData

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    fun initRepository(application: Application) {
        mFavoriteUserRepository = FavoriteUserRepository(application)
    }

    fun setUser(user: GithubUser) {
        _responseLiveData.postValue(user)
    }

    fun fetchGitHubUsers(username: String) {
        _isLoadingLiveData.postValue(true)
        val call = gitHubService.getUserDetail(username)
        call.enqueue(object : Callback<GithubUser> {
            override fun onResponse(
                call: Call<GithubUser>, response: Response<GithubUser>
            ) {
                _isLoadingLiveData.postValue(false)
                if (response.isSuccessful) {
                    val gitHubResponse = response.body() ?: return
                    val isFavorite = favoriteUsersLiveData?.value?.any {
                        gitHubResponse.id == it.id
                    } ?: false
                    gitHubResponse.isFavorite = isFavorite
                    _responseLiveData.postValue(gitHubResponse)
                }
            }

            override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                _isLoadingLiveData.postValue(false)
                _errorLiveData.postValue(t)
            }
        })
    }

    fun getFavoriteUsers() {
        favoriteUsersLiveData = mFavoriteUserRepository?.getAllFavoriteUsers()
    }

    fun toggleFavoriteUser() {
        viewModelScope.launch {
            responseLiveData.value?.let { user ->
                if (user.isFavorite) mFavoriteUserRepository?.delete(user)
                else mFavoriteUserRepository?.insert(user)

                user.isFavorite = !user.isFavorite

                _responseLiveData.postValue(user)
            }
        }

    }


}