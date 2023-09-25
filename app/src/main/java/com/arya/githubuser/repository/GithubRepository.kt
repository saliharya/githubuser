package com.arya.githubuser.repository

import com.arya.githubuser.api.GitHubResponse
import com.arya.githubuser.api.GitHubService
import com.arya.githubuser.model.GithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRepository {
    private val apiKey = "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"
    private val gitHubService = GitHubService.create(apiKey)

    fun fetchGithubUsers(
        query: String,
        onSuccess: (GitHubResponse?) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val call = gitHubService.searchUsers(query)
        call.enqueue(object : Callback<GitHubResponse> {
            override fun onResponse(
                call: Call<GitHubResponse>, response: Response<GitHubResponse>
            ) {
                onSuccess(response.body())
            }

            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun fetchGithubUserDetail(
        username: String,
        onSuccess: (GithubUser?) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val call = gitHubService.getUserDetail(username)
        call.enqueue(object : Callback<GithubUser> {
            override fun onResponse(
                call: Call<GithubUser>, response: Response<GithubUser>
            ) {
                onSuccess(response.body())
            }

            override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun getFollowers(
        username: String,
        onSuccess: (List<GithubUser>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val call = gitHubService.getFollowers(username)
        call.enqueue(object : Callback<List<GithubUser>> {
            override fun onResponse(
                call: Call<List<GithubUser>>, response: Response<List<GithubUser>>
            ) {
                onSuccess(response.body().orEmpty())
            }

            override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun getFollowings(
        username: String,
        onSuccess: (List<GithubUser>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val call = gitHubService.getFollowing(username)
        call.enqueue(object : Callback<List<GithubUser>> {
            override fun onResponse(
                call: Call<List<GithubUser>>, response: Response<List<GithubUser>>
            ) {
                onSuccess(response.body().orEmpty())
            }

            override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}