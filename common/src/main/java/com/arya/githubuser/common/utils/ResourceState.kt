package com.arya.githubuser.common.utils

import com.google.gson.annotations.SerializedName

sealed class ResourceState<T> {
    data class Loading<T>(val data: T? = null): ResourceState<T>()
    data class Success<T>(val data: T?) : ResourceState<T>()
    data class Error<T>(
        @SerializedName("message") val message: String? = Constants.SOMETHING_WENT_WRONG,
        val data: T? = null,
    ) : ResourceState<T>()
}