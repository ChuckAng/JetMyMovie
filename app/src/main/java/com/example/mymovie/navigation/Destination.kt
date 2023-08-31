package com.example.mymovie.navigation

sealed class Destination(val route: String) {
    object SplashScreen : Destination("splash_screen")
    object HomeScreen : Destination("home_screen")
    object SearchScreen : Destination("search_screen")
    object TicketScreen : Destination("ticket_screen")
    object ProfileScreen : Destination("profile_screen")
    object MovieDetailScreen : Destination("movie_detail_screen")
}
