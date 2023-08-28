package com.example.core.networking

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T: Any> apiRequest(call: suspend () -> Response<T>): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = call.invoke()
            val body = response.body()
            val errorBody = response.errorBody()
            Log.d("metag", "response: " + response)

            if (response.isSuccessful && body != null) {
                Resource.Success(body)
            } else {
                val errorMessage = errorBody?.string() ?: "Unknown error"
                Log.d("metag", "else: " + errorMessage)
                Resource.Error(errorMessage)
            }
        } catch (ex: UnknownHostException) {
            Log.d("metag", "apiRequest: called!!!")
            ResponseHandler.handleResponse(Resource.Exception(ex))
        } catch (ex: Exception) {
            Log.d("metag", "exxxxx " + ex.message)
            Resource.Exception(ex)
        }
    }
}
