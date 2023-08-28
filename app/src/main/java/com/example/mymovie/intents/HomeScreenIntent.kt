package com.example.mymovie.intents

sealed class HomeScreenIntent {
    object GetMovieEvent : HomeScreenIntent()
}
