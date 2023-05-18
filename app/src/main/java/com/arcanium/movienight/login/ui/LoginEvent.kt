package com.arcanium.movienight.login.ui

sealed class LoginEvent {
    object AuthSuccess : LoginEvent()
    object AuthFailure : LoginEvent()
    object NoInternetConnection : LoginEvent()
    object ApiError : LoginEvent()
}