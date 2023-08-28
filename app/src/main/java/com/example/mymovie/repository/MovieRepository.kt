package com.example.mymovie.repository

import com.example.core.networking.RetroClient
import com.example.core.networking.apiRequest
import com.example.mymovie.networking.Api

class MovieRepository {

    suspend fun getMovies() = apiRequest {
        RetroClient.client.create(Api::class.java).getMovies().execute()
    }

}