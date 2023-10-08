package com.arya.githubuser.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arya.githubuser.model.GithubUser
import com.arya.githubuser.repository.FavoriteUserRepository
import com.arya.githubuser.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
    private var mFavoriteUserRepository: FavoriteUserRepository
) : ViewModel() {

    private val _responseLiveData: MutableLiveData<GithubUser> = MutableLiveData()
    val responseLiveData: LiveData<GithubUser> = _responseLiveData

    private val _favoriteUsersLiveData: MutableLiveData<List<GithubUser>> = MutableLiveData()
    var favoriteUsersLiveData: LiveData<List<GithubUser>> = _favoriteUsersLiveData

    private val _errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val errorLiveData: LiveData<Throwable> = _errorLiveData

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    fun setUser(user: GithubUser) {
        _responseLiveData.postValue(user)
    }

    fun fetchGitHubUser(username: String) {
        _isLoadingLiveData.postValue(true)
        githubRepository.fetchGithubUserDetail(username, onSuccess = { response ->
            _isLoadingLiveData.postValue(false)
            val isFavorite = favoriteUsersLiveData.value?.any {
                response?.id == it.id
            } ?: false
            response?.isFavorite = isFavorite
            _responseLiveData.postValue(response)
        }, onFailure = {
            _isLoadingLiveData.postValue(false)
            _errorLiveData.postValue(it)
        })
    }

    fun getFavoriteUsers() {
        viewModelScope.launch {
            _favoriteUsersLiveData.postValue(mFavoriteUserRepository.getFavoriteUsers())
        }
    }

    fun toggleFavoriteUser() {
        viewModelScope.launch {
            responseLiveData.value?.let { user ->
                if (user.isFavorite) mFavoriteUserRepository.delete(user)
                else mFavoriteUserRepository.insert(user)

                user.isFavorite = !user.isFavorite

                _responseLiveData.postValue(user)
            }
        }

    }


}