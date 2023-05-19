package com.arcanium.movienight

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.arcanium.movienight.login.ui.LoginScreen
import com.arcanium.movienight.login.ui.LoginViewModel
import com.arcanium.movienight.navigation.NavDestination

@Composable
internal fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavDestination.Login) {
        composable(route = "login") {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            val loginUiState by loginViewModel.loginUiState.collectAsState()
            LoginScreen(
                loginUiState = loginUiState,
                loginOnClickListener = loginViewModel.onClickListener,
            )
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