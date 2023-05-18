package com.arcanium.movienight.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

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
    val textFieldHeight = ((screenHeight * 0.15f).toInt()).dp
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(textFieldHeight)
                .padding(bottom = 20.dp),
            value = loginUiState.username,
            onValueChange = {
                loginOnClickListener.onUsernameChanged(username = it)
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.7f)
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
            }
        )
        Button(onClick = loginOnClickListener::onLoginButtonClicked) {
            Text(text = "Login")
        }
    }
}