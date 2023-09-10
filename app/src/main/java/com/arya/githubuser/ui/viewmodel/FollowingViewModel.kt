package com.arya.githubuser.ui.viewmodel

import com.arya.githubuser.model.GithubUser
import retrofit2.Call

class FollowingViewModel : BaseFollowingFollowerViewModel() {
    override fun callApi(username: String): Call<List<GithubUser>> {
        return gitHubService.getFollowing(apiKey, username)
    }

}