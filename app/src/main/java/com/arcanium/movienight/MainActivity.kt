package com.arcanium.movienight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arcanium.movienight.login.ui.LoginScreen
import com.arcanium.movienight.login.ui.LoginViewModel
import com.arcanium.movienight.theme.MovieNightTheme
import com.arcanium.navigation.NavDestination

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieNightTheme {
                AppNavHost()
            }
        }
    }
}