package com.arcanium.movienight

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.arcanium.movienight.navigation.NavDestination
import com.arcanium.movienight.onboarding.login.ui.routeLoginScreen

@Composable
internal fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavDestination.Login) {
        routeLoginScreen()
    }
}

@Composable
internal fun NavHost(
    navController: NavHostController,
    startDestination: NavDestination,
    modifier: Modifier = Modifier,
    route: NavDestination? = null,
    builder: NavGraphBuilder.() -> Unit
) {
    androidx.navigation.compose.NavHost(
        navController,
        remember(route?.route, startDestination.route, builder) {
            navController.createGraph(startDestination.route, route?.route, builder)
        },
        modifier
    )
}