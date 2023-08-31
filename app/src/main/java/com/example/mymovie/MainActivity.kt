package com.example.mymovie

import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.mymovie.navigation.NavigationView
import com.example.mymovie.ui.theme.MyMovieTheme

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMovieTheme {
                NavigationView()
            }
        }
    }
}