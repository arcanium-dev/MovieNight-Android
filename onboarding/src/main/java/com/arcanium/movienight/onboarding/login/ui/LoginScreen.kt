package com.arcanium.movienight.onboarding.login.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import com.arcanium.movienight.navigation.NavDestination
import com.arcanium.movienight.navigation.composable
import com.arcanium.movienight.onboarding.R
import com.arcanium.movienight.util.collectAsEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoginScreen(
    loginUiState: LoginUiState,
    error: LoginError?,
    loginOnClickListener: LoginOnClickListener
) {
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
        Spacer(modifier = Modifier.weight(1f))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(componentWidth)
                .height(80.dp)
                .padding(bottom = 20.dp),
            value = loginUiState.username,
            label = {
                Text(text = stringResource(id = R.string.username_label))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                loginOnClickListener.onUsernameChanged(username = it)
            },
            singleLine = true,
            colors =  if (error is LoginError.InvalidUsername) TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.error,
                unfocusedBorderColor = MaterialTheme.colorScheme.error
            ) else TextFieldDefaults.outlinedTextFieldColors()
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(componentWidth)
                .height(80.dp)
                .padding(bottom = 20.dp),
            value = loginUiState.password,
            label = {
                Text(text = stringResource(id = R.string.password_label))
            },
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
            singleLine = true,
            colors =  if (error is LoginError.InvalidPassword) TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.error,
                unfocusedBorderColor = MaterialTheme.colorScheme.error
            ) else TextFieldDefaults.outlinedTextFieldColors()
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
        Spacer(modifier = Modifier.weight(1f))
        when {
            error != null -> {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = stringResource(id = error.errorMessageId),
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}


fun NavGraphBuilder.routeLoginScreen(
    navigateToHome: () -> Unit
) {
    composable(navDestination = NavDestination.Login) {
        val loginViewModel = hiltViewModel<LoginViewModel>()
        val loginUiState by loginViewModel.loginUiState.collectAsStateWithLifecycle()
        val errorState by loginViewModel.error.collectAsStateWithLifecycle()

        LoginScreen(
            loginUiState = loginUiState,
            error = errorState,
            loginOnClickListener = loginViewModel.onClickListener
        )

        loginViewModel.authSuccess.collectAsEffect {
            navigateToHome()
        }
    }
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    showBackground = true,
    showSystemUi = true
)
private fun LoginScreenPreview() {
    LoginScreen(
        loginUiState = LoginUiState(),
        error = null,
        loginOnClickListener = LoginOnClickListener.previewObject
    )
}