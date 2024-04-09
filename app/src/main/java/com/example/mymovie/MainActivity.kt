package com.example.mymovie

import android.annotation.SuppressLint
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
import com.example.core.CoreModule
import com.example.mymovie.navigation.BottomNavigation
import com.example.mymovie.navigation.Destination
import com.example.mymovie.navigation.NavigationView
import com.example.mymovie.ui.theme.MyMovieTheme

class MainActivity : BaseActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreModule.init(application)
        setContent {
            MyMovieTheme {
                val navController = rememberNavController()
                var isShowBottomBar by remember { mutableStateOf(false) }

                navController.addOnDestinationChangedListener(object :
                    NavController.OnDestinationChangedListener {
                    override fun onDestinationChanged(
                        controller: NavController,
                        destination: NavDestination,
                        arguments: Bundle?
                    ) {
                        val currentRoute = controller.currentDestination?.route
                        isShowBottomBar = currentRoute != Destination.SplashScreen.route
                                && currentRoute != Destination.LoginScreen.route
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