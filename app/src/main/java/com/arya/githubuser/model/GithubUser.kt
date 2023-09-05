package com.arya.githubuser.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubUser(
    val login: String,
    val id: Long,
    val avatar_url: String,
    val html_url: String,
    // Add other properties as needed
) : Parcelable

