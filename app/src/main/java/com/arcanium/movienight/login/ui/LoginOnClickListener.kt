package com.arcanium.movienight.login.ui

internal interface LoginOnClickListener {
    fun onUsernameChanged(username: String)
    fun onPasswordChanged(password: String)
    fun onLoginButtonClicked()
}