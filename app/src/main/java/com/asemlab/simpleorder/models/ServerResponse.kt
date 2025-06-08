package com.asemlab.simpleorder.models

sealed class ServerResponse<T> {

    data class Success<T>(val responseData: T) : ServerResponse<T>()

    data class Error<T>(val errorBody: String, val code: Int) : ServerResponse<T>()

}

suspend fun <T> ServerResponse<T>.onSuccess(onSuccess: suspend (T) -> Unit): ServerResponse<T> {
    when (this) {
        is ServerResponse.Success -> onSuccess(responseData)
        else -> {}
    }

    return this
}

fun <T> ServerResponse<T>.onError(onError: (String) -> Unit): ServerResponse<T> {
    when (this) {
        is ServerResponse.Error -> onError(errorBody)
        else -> {}
    }
    return this
}