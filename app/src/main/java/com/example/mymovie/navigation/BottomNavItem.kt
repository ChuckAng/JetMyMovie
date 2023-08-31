package com.example.mymovie.navigation

import com.example.mymovie.R

sealed class BottomNavItem(val icon: Int) {
    object Home : BottomNavItem(R.drawable.home_icon)
    object Profile : BottomNavItem(R.drawable.person_icon)
    object Ticket : BottomNavItem(R.drawable.person_icon)
    object Setting : BottomNavItem(R.drawable.person_icon)
}
