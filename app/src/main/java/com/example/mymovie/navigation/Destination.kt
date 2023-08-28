package com.example.mymovie.navigation

sealed class Destination(val route: String) {
    object SplashScreen : Destination("splash_screen")
    object HomeScreen : Destination("home_screen")
}
