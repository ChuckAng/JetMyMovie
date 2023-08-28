package com.example.core.networking

sealed class Resource<out T> {
    data class Success<T>(val data: T): Resource<T>()
    data class Error<T>(val errorMsg: String?) : Resource<T>()
    data class Exception<T>(val throwable: Throwable, val errorMsg: String? = null) : Resource<T>()
}
