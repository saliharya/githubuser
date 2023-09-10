package com.arya.githubuser.ui.viewmodel

import com.arya.githubuser.model.GithubUser
import retrofit2.Call

class FollowerViewModel : BaseFollowingFollowerViewModel() {
    override fun callApi(username: String): Call<List<GithubUser>> {
        return gitHubService.getFollowers(apiKey, username)
    }

}