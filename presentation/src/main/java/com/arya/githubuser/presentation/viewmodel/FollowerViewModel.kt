package com.arya.githubuser.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.arya.githubuser.core.domain.usecase.GetFollowerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowerViewModel @Inject constructor(
    private val getFollowerUseCase: GetFollowerUseCase
) : BaseFollowingFollowerViewModel() {
    override fun fetchData(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getFollowerUseCase(username).collectLatest(_responseLiveData::postValue)
        }
    }
}