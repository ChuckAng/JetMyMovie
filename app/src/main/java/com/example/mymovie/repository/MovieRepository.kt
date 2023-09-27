package com.example.mymovie.repository

import com.example.core.networking.RetroClient
import com.example.core.networking.apiRequest
import com.example.mymovie.networking.Api

class MovieRepository {

    suspend fun getMovies(
        searchKey: String?,
        skip: Int?,
        take: Int?
    ) = apiRequest {
        RetroClient.client.create(Api::class.java).getMovies(searchKey, skip, take).execute()
    }
}