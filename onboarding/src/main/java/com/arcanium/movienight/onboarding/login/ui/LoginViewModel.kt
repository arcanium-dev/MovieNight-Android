package com.arcanium.movienight.onboarding.login.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.arcanium.movienight.domain.Resource
import com.arcanium.movienight.domain.user.UserRepository
import com.arcanium.movienight.presentation.EventViewModel
import com.arcanium.movienight.util.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
internal class LoginViewModel
@Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationContext private val appContext: Context
) : EventViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState>
        get() = _loginUiState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = _loginUiState.value
        )
    private val currentUiState: LoginUiState
        get() = loginUiState.value

    private val _error = MutableStateFlow<LoginError?>(null)
    val error: StateFlow<LoginError?>
        get() = _error.asStateFlow()

    private val _authSuccess: MutableSharedFlow<Unit> = MutableSharedFlow()
    val authSuccess: SharedFlow<Unit?>
        get() = _authSuccess.asSharedFlow()

    val onClickListener = object : LoginOnClickListener {
        override fun onUsernameChanged(username: String) {
            _loginUiState.update { it.copy(username = username) }
        }

        override fun onPasswordChanged(password: String) {
            _loginUiState.update { it.copy(password = password) }
        }

        override fun onLoginButtonClicked() {
            loginUserWithEmail()
        }
    }

    private fun loginUserWithEmail() = viewModelScope.launch {
        updatingLoading(true)
        if (currentUiState.username.isBlank()) {
            updatingLoading(false)
            _error.emitEvent(LoginError.InvalidUsername)
            return@launch
        }
        if (currentUiState.password.isBlank()) {
            updatingLoading(false)
            _error.emitEvent(LoginError.InvalidPassword)
            return@launch
        }
        if (!appContext.isNetworkAvailable()) {
            updatingLoading(false)
            _error.emitEvent(LoginError.NoInternetConnection)
            return@launch
        }
        val loginResult = userRepository.loginWithEmailAndPassword(
            email = currentUiState.username.trim(),
            password = currentUiState.password.trim()
        )
        when (loginResult) {
            is Resource.Success -> {
                updatingLoading(false)
                debugLog("Login successful")
                _authSuccess.emitEvent(event = Unit)
            }
            is Resource.Failure -> {
                updatingLoading(false)
                debugLog("Login failed with exception=${loginResult.exception?.localizedMessage}")
                _error.emitEvent(event = LoginError.AuthFailure)
            }
        }
    }

    private fun updatingLoading(state: Boolean) {
        _loginUiState.update {
            it.copy(isLoading = state)
        }
    }

}