package com.example.mymovie.states

import com.example.mymovie.models.MovieModel

sealed class HomeScreenState {
    data class Success(val data: List<MovieModel>) : HomeScreenState()
    data class Error(val errorMsg: String) : HomeScreenState()
    object Loading : HomeScreenState()
}