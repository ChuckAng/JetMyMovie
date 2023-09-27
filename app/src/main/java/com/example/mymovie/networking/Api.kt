package com.example.mymovie.networking

import com.example.mymovie.models.MovieModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("movies")
    fun getMovies(
        @Query("searchKey") searchKey: String?,
        @Query("skip") skip: Int?,
        @Query("take") take: Int?
    ): Call<List<MovieModel>>
}