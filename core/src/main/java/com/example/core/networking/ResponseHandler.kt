package com.example.core.networking

import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

object ResponseHandler {

    fun <T> handleResponse(response: Resource<T>): Resource<T> {
        return when (response) {
            is Resource.Success -> handleSuccess(response.data)
            is Resource.Error -> handleError(response.errorMsg)
            is Resource.Exception -> handleException(response.throwable)
        }
    }

    fun <T> handleSuccess(data: T): Resource<T> {
        return Resource.Success(data)
    }

    private fun <T> handleError(error: String?): Resource<T> {
        val errorMsg = "Unknown error"
        return Resource.Error(errorMsg)
    }

    private fun <T> handleException(throwable: Throwable): Resource<T> {
        val errorMsg =
            if (throwable is SocketTimeoutException || throwable is UnknownHostException || throwable is SSLException)
                "Poor internet connection"
            else "Unknown error"
        return Resource.Exception(throwable, errorMsg)
    }
}