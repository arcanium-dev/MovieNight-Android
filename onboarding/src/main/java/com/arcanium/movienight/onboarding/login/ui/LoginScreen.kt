package com.arcanium.movienight.onboarding.login.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import com.arcanium.movienight.navigation.NavDestination
import com.arcanium.movienight.navigation.composable
import com.arcanium.movienight.util.collectAsEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoginScreen(
    loginUiState: LoginUiState,
    loginOnClickListener: LoginOnClickListener
) {
    val localConfiguration = LocalConfiguration.current
    val screenHeight = remember {
        localConfiguration.screenHeightDp
    }
    val textFieldHeight = ((screenHeight * 0.10f).toInt()).dp
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    val componentWidth = 0.80f
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = focusManager::clearFocus
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(componentWidth)
                .height(textFieldHeight)
                .padding(bottom = 20.dp),
            value = loginUiState.username,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                loginOnClickListener.onUsernameChanged(username = it)
            },
            singleLine = true
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(componentWidth)
                .height(textFieldHeight)
                .padding(bottom = 20.dp),
            value = loginUiState.password,
            onValueChange = {
                loginOnClickListener.onPasswordChanged(password = it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Show password"
                    )
                }
            },
            singleLine = true
        )
        Button(
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth(componentWidth)
                .imePadding(),
            onClick = {
                focusManager.clearFocus()
                loginOnClickListener.onLoginButtonClicked()
            },
            shape = CircleShape,
            enabled = !loginUiState.isLoading
        ) {
            Text(text = "Login")
        }
    }
}


fun NavGraphBuilder.routeLoginScreen(
    navigateToHome: () -> Unit
) {
    composable(navDestination = NavDestination.Login) {
        Box(modifier = Modifier.fillMaxSize()) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            val loginUiState by loginViewModel.loginUiState.collectAsStateWithLifecycle()
            val snackbarHostState = remember { SnackbarHostState() }
            SnackbarHost(
                modifier = Modifier.align(Alignment.BottomCenter),
                hostState = snackbarHostState
            )
            LoginScreen(
                loginUiState = loginUiState,
                loginOnClickListener = loginViewModel.onClickListener
            )
            loginViewModel.error.collectAsEffect { error ->
                showSnackBarErrorMessages(snackbarHostState = snackbarHostState, loginError = error)
            }
            loginViewModel.authSuccess.collectAsEffect {
                navigateToHome()
            }
        }
    }
}

private suspend fun showSnackBarErrorMessages(
    snackbarHostState: SnackbarHostState,
    loginError: LoginError
) {
    when(loginError) {
        LoginError.ApiError -> {
            snackbarHostState.showSnackbar(
                message = "An Api error has occurred. Please contact support.",
                actionLabel = "Dismiss",
                duration = SnackbarDuration.Short
            )
        }
        LoginError.AuthFailure -> {
            snackbarHostState.showSnackbar(
                message = "Incorrect credentials, please try again.",
                actionLabel = "Dismiss",
                duration = SnackbarDuration.Short
            )
        }
        LoginError.InvalidPassword -> {
            snackbarHostState.showSnackbar(
                message = "Password is in an invalid format.",
                actionLabel = "Dismiss",
                duration = SnackbarDuration.Short
            )
        }
        LoginError.InvalidUsername -> {
            snackbarHostState.showSnackbar(
                message = "Username is in an invalid format.",
                actionLabel = "Dismiss",
                duration = SnackbarDuration.Short
            )
        }
        LoginError.NoInternetConnection -> {
            snackbarHostState.showSnackbar(
                message = "No internet connection.",
                actionLabel = "Dismiss",
                duration = SnackbarDuration.Short
            )
        }
    }
}