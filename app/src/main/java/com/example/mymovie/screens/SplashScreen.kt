package com.example.mymovie.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mymovie.R
import com.example.mymovie.navigation.Destination
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SplashScreen(navController: NavController, onHome: () -> Unit) {
    val systemUiController = rememberSystemUiController()
    systemUiController.isStatusBarVisible = false
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    ) {
        Column(modifier = Modifier.padding(88.dp)) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.logo))
            val animationState = animateLottieCompositionAsState(composition = composition)
            LottieAnimation(composition = composition, progress = animationState.progress)
            if (animationState.isAtEnd && animationState.isPlaying) {
                navController.navigate(Destination.HomeScreen.route)
            }
        }
    }
}