package com.example.mymovie

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.rememberNavController
import com.example.mymovie.navigation.BottomNavigation
import com.example.mymovie.navigation.Destination
import com.example.mymovie.navigation.NavigationView
import com.example.mymovie.ui.theme.MyMovieTheme

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMovieTheme {
                val navController = rememberNavController()
                var isShowBottomBar by remember { mutableStateOf(true) }

                navController.addOnDestinationChangedListener(object :
                    NavController.OnDestinationChangedListener {
                    override fun onDestinationChanged(
                        controller: NavController,
                        destination: NavDestination,
                        arguments: Bundle?
                    ) {
                        val currentRoute = controller.currentDestination?.route
                        isShowBottomBar = currentRoute != Destination.SplashScreen.route
                    }
                })
                Scaffold(
                    bottomBar = {
                        if (isShowBottomBar) BottomNavigation(navController = navController)
                    }
                ) {
                    NavigationView(navController)
                }
            }
        }
    }
}