package com.arya.githubuser.common.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response

inline fun <reified T, R> Response<T>.tryParse(mapper: (T?) -> R?): ResourceState<R> = try {
    if (isSuccessful) {
        ResourceState.Success(data = mapper(body()))
    } else {
        val errorResponse: ResourceState.Error<R> = Gson().fromJson(
            errorBody()?.charStream(),
            object : TypeToken<ResourceState.Error<R>>() {}.type
        )

        if (errorResponse.message.isNullOrEmpty()) {
            errorResponse.copy(message = Constants.SOMETHING_WENT_WRONG)
        } else {
            errorResponse
        }
    }
} catch (e: Exception) {
    ResourceState.Error(message = e.localizedMessage ?: Constants.SOMETHING_WENT_WRONG)
}