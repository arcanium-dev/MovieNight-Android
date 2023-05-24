package com.arcanium.movienight

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.arcanium.movienight.navigation.NavDestination
import com.arcanium.movienight.navigation.composable
import com.arcanium.movienight.navigation.navigate
import com.arcanium.movienight.onboarding.login.ui.routeLoginScreen

@Composable
internal fun AppNavHost(startDestination: NavDestination) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        routeLoginScreen(
            navigateToHome = {
                navController.navigate(navDestination = NavDestination.Home)
            }
        )
        composable(navDestination = NavDestination.Home) {
            Box(modifier = Modifier.fillMaxSize())
        }
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