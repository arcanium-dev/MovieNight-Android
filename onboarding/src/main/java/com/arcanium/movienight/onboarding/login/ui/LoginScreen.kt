package com.arcanium.movienight.onboarding.login.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import com.arcanium.movienight.navigation.NavDestination
import com.arcanium.movienight.navigation.composable

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
            visualTransformation = PasswordVisualTransformation(),
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
            onClick = loginOnClickListener::onLoginButtonClicked,
            shape = CircleShape
        ) {
            Text(text = "Login")
        }
    }
}


fun NavGraphBuilder.routeLoginScreen() {
    composable(navDestination = NavDestination.Login) {
        val loginViewModel = hiltViewModel<LoginViewModel>()
        val loginUiState by loginViewModel.loginUiState.collectAsStateWithLifecycle()
        LoginScreen(
            loginUiState = loginUiState,
            loginOnClickListener = loginViewModel.onClickListener
        )
    }
}