package com.arcanium.movienight.onboarding.login.ui

sealed class LoginError {
    object InvalidUsername : LoginError()
    object InvalidPassword : LoginError()
    object AuthFailure : LoginError()
    object NoInternetConnection : LoginError()
    object ApiError : LoginError()
}