package com.arya.githubuser.api

import com.arya.githubuser.model.GitHubUser

data class GitHubResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<GitHubUser>
)
