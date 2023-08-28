package com.example.mymovie.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class MovieModel(
    val id: Int?,
    val title: String?,
    val description: String?,
    val imageUrl: String?
) {
    companion object {
        @JvmStatic
        fun deserializeList(json: String): List<MovieModel> {
            return Gson().fromJson(json, object : TypeToken<List<MovieModel>>() {}.type)
        }
    }
}
