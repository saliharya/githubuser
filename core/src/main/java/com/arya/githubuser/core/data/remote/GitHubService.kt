package com.arya.githubuser.core.data.remote

import com.arya.githubuser.core.data.model.GitHubResponse
import com.arya.githubuser.core.data.model.GithubUserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String): Response<GitHubResponse>

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): Response<GithubUserDto>

    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): Response<List<GithubUserDto>>

    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): Response<List<GithubUserDto>>
}
