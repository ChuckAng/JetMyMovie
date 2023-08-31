package com.example.mymovie.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymovie.screens.HomeScreen
import com.example.mymovie.screens.MovieDetailScreen
import com.example.mymovie.screens.SplashScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigationView() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destination.SplashScreen.route) {
        composable(Destination.SplashScreen.route) {
            SplashScreen(
                onHome = {
                    navController.navigate(Destination.HomeScreen.route)
                }
            )
        }
        composable(Destination.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(Destination.MovieDetailScreen.route + "/{movie_obj}") { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("movie_obj")?.let { movie ->
                MovieDetailScreen(movie)
            }
        }
    }
}