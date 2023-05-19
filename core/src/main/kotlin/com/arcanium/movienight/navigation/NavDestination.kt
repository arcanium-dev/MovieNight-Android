package com.arcanium.movienight.navigation

sealed class NavDestination(val route: String) {
    object Login : NavDestination(route = "login")
}