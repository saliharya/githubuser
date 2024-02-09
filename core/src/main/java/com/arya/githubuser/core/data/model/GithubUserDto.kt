package com.arya.githubuser.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GithubUserDto(
    @PrimaryKey val id: Long?,
    val login: String?,
    val name: String?,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("html_url") val htmlUrl: String?,
    val followers: Int?,
    val following: Int?,
    var isFavorite: Boolean = false
)

