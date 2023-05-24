package com.arcanium.movienight.onboarding.login.ui

import com.arcanium.movienight.onboarding.R

sealed class LoginError(
    val errorMessageId: Int
) {
    object InvalidUsername : LoginError(errorMessageId = R.string.username_invalid_error)
    object InvalidPassword : LoginError(errorMessageId = R.string.password_invalid_error)
    object AuthFailure : LoginError(errorMessageId = R.string.auth_failure)
    object NoInternetConnection : LoginError(errorMessageId = R.string.no_network_connectivity)
    object ApiError : LoginError(errorMessageId = R.string.api_error)
}