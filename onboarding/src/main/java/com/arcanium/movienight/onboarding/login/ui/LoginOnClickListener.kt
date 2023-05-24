package com.arcanium.movienight.onboarding.login.ui

internal interface LoginOnClickListener {
    fun onUsernameChanged(username: String)
    fun onPasswordChanged(password: String)
    fun onLoginButtonClicked()

    companion object {
        val previewObject = object : LoginOnClickListener {
            override fun onUsernameChanged(username: String) = Unit
            override fun onPasswordChanged(password: String) = Unit
            override fun onLoginButtonClicked() = Unit
        }
    }
}