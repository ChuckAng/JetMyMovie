package com.example.mymovie.states

import com.example.mymovie.models.MovieModel

sealed class SearchScreenState {
    data class Success(val data: List<MovieModel>) : SearchScreenState()
    data class Error(val errorMsg: String) : SearchScreenState()
    object Loading : SearchScreenState()
    object Idle: SearchScreenState()
}