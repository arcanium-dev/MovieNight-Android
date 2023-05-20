package com.arcanium.movienight.onboarding.login.ui

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)
