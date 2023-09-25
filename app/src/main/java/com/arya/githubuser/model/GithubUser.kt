package com.arya.githubuser.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class GithubUser(
    @PrimaryKey
    val id: Long?,
    val login: String?,
    val name: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("html_url")
    val htmlUrl: String?,
    val followers: Int?,
    val following: Int?,
    var isFavorite: Boolean = false
) : Parcelable

