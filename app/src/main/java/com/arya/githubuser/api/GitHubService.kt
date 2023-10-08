package com.arya.githubuser.api

import com.arya.githubuser.model.GithubUser
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Call<GitHubResponse>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<GithubUser>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<GithubUser>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<GithubUser>>

}
