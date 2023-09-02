package com.example.mymovie.models

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieModel(
    val id: Int?,
    val title: String?,
    val description: String?,
    val videoUrl: String?,
    val imageUrl: String?,
    val price: String?,
    val rating: Double?,
    val vote: Int?,
    val genre: String?,
    val releaseDate: String?,
    val spokenLanguage: String?,
    val duration: String?,
    val director: String?,
    val cast: String?
) : Parcelable {
    companion object {
        @JvmStatic
        fun deserializeList(json: String): List<MovieModel> {
            return Gson().fromJson(json, object : TypeToken<List<MovieModel>>() {}.type)
        }
    }
}
