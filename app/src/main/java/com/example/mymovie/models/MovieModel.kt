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
    val imageUrl: String?
) : Parcelable {
    companion object {
        @JvmStatic
        fun deserializeList(json: String): List<MovieModel> {
            return Gson().fromJson(json, object : TypeToken<List<MovieModel>>() {}.type)
        }
    }
}
