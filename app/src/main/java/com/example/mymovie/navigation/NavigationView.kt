package com.example.mymovie.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mymovie.screens.HomeScreen
import com.example.mymovie.screens.LoginScreen
import com.example.mymovie.screens.MovieDetailScreen
import com.example.mymovie.screens.ProfileScreen
import com.example.mymovie.screens.SearchScreen
import com.example.mymovie.screens.SplashScreen
import com.example.mymovie.screens.TicketDetailScreen
import com.example.mymovie.screens.TicketScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigationView(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destination.SplashScreen.route) {
        composable(Destination.SplashScreen.route) {
            SplashScreen(
                gotoLogin = {
//                    navController.navigate(Destination.LoginScreen.route)
                    //todo: temporary to home screen for demo purpose
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
        composable(Destination.TicketDetailScreen.route) {
            TicketDetailScreen()
        }
        composable(Destination.TicketScreen.route) {
            TicketScreen()
        }
        composable(Destination.SearchScreen.route) {
            SearchScreen()
        }
        composable(Destination.LoginScreen.route) {
            LoginScreen()
        }
        composable(Destination.ProfileScreen.route) {
            ProfileScreen()
        }
    }
}