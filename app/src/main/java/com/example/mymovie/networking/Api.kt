package com.example.mymovie.networking

import com.example.mymovie.models.MovieModel
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("messages")
    fun getMovies() : Call<List<MovieModel>>
}