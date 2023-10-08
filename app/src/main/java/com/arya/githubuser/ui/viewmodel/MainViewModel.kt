package com.arya.githubuser.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.arya.githubuser.api.GitHubResponse
import com.arya.githubuser.datastore.SettingPreferences
import com.arya.githubuser.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pref: SettingPreferences,
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val _responseLiveData: MutableLiveData<GitHubResponse> = MutableLiveData()
    val responseLiveData: LiveData<GitHubResponse> = _responseLiveData

    private val _errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val errorLiveData: LiveData<Throwable> = _errorLiveData

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun fetchGitHubUsers(query: String) {
        _isLoadingLiveData.postValue(true)
        githubRepository.fetchGithubUsers(query, onSuccess = { response ->
            _isLoadingLiveData.postValue(false)
            response?.let {
                _responseLiveData.postValue(it)
            }
        }, onFailure = {
            _isLoadingLiveData.postValue(false)
            _errorLiveData.postValue(it)
        })
    }
}