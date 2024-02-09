package com.arya.githubuser.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.arya.githubuser.core.domain.usecase.GetFollowingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val getFollowingUseCase: GetFollowingUseCase
) : BaseFollowingFollowerViewModel() {
    override fun fetchData(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getFollowingUseCase(username).collectLatest(_responseLiveData::postValue)
        }
    }
}