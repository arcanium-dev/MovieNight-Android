package com.arcanium.movienight

import androidx.lifecycle.ViewModel
import com.arcanium.movienight.data.user.UserRepository
import com.arcanium.movienight.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val currentLoggedInUser = userRepository.currentLoggedInUser
    val startDestination = currentLoggedInUser.let {
        if (it == null) {
            Timber.tag(this.javaClass.simpleName).i("Null user - navigating to Login")
            NavDestination.Login
        } else {
            Timber.tag(this.javaClass.simpleName).i("User found: ${it.username}")
            NavDestination.Home
        }
    }
}