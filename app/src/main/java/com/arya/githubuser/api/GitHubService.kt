package com.arya.githubuser.api

import com.arya.githubuser.model.GithubUser
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @GET("search/users")
    fun searchUsers(
        @Header("Authorization") apiKey: String,
        @Query("q") query: String
    ): Call<GitHubResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Header("Authorization") apiKey: String,
        @Path("username") username: String
    ): Call<GithubUser>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Header("Authorization") apiKey: String,
        @Path("username") username: String
    ): Call<List<GithubUser>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Header("Authorization") apiKey: String,
        @Path("username") username: String
    ): Call<List<GithubUser>>

    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): GitHubService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(GitHubService::class.java)
        }
    }
}
