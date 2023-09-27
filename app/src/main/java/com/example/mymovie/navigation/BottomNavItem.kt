package com.example.mymovie.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mymovie.R

sealed class BottomNavItem(val icon: Int, val route: String) {
    object Home : BottomNavItem(R.drawable.home_icon, Destination.HomeScreen.route)
    object Profile : BottomNavItem(R.drawable.person_icon, Destination.ProfileScreen.route)
    object Ticket : BottomNavItem(R.drawable.ticket_icon, Destination.TicketScreen.route)
    object Search : BottomNavItem(R.drawable.search_icon, Destination.SearchScreen.route)
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Ticket,
        BottomNavItem.Search,
        BottomNavItem.Profile
    )
    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navStackBackEntry?.destination

    NavigationBar(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp))),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        items.forEach { item ->
            AddItem(
                screen = item,
                currentRoute = currentRoute,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavItem,
    currentRoute: NavDestination?,
    navController: NavController,

    ) {
    val selectedItem = currentRoute?.hierarchy?.any { it.route == screen.route } == true

    NavigationBarItem(
        modifier = Modifier.align(Alignment.CenterVertically),
        selected = selectedItem,
        onClick = {
            navController.navigate(screen.route) {
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        icon = { Icon(painter = painterResource(screen.icon), contentDescription = null) },
        colors = NavigationBarItemDefaults.colors(),
    )
}

