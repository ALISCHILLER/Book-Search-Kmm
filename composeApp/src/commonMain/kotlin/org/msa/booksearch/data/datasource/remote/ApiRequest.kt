package org.msa.booksearch.data.datasource.remote

import io.ktor.http.HttpMethod

// Base class for making API requests with dynamic parameters.
data class ApiRequest(
    val endpoint: String,
    val method: HttpMethod = HttpMethod.Get,
    val parameters: Map<String, String> = emptyMap(),
    val body: Any? = null
)
// Base response class to handle various types of responses.
sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T): ApiResponse<T>()
    data class Error(val exception: Throwable): ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
}

