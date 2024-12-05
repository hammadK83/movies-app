package com.sampleapp.movies.data.model

sealed class ApiError {
    data object NetworkError : ApiError()                                   // No internet connection
    data object TimeoutError : ApiError()                                   // Request timed out
    data class HttpError(val code: Int, val message: String) : ApiError()   // API returned an error
    data class UnknownError(val message: String?) : ApiError()              // Other unexpected errors
}