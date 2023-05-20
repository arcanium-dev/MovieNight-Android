package com.arcanium.movienight.onboarding.login.ui

sealed class LoginEvent {
    object AuthSuccess : LoginEvent()
    object AuthFailure : LoginEvent()
    object NoInternetConnection : LoginEvent()
    object ApiError : LoginEvent()
}