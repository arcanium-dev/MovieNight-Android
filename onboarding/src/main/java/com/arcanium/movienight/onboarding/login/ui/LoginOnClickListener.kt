package com.arcanium.movienight.onboarding.login.ui

internal interface LoginOnClickListener {
    fun onUsernameChanged(username: String)
    fun onPasswordChanged(password: String)
    fun onLoginButtonClicked()
}