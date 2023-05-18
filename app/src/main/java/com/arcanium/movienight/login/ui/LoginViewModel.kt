package com.arcanium.movienight.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcanium.data.user.UserRepository
import com.arcanium.domain.user.model.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState>
        get() = _loginUiState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = _loginUiState.value
        )
    private val currentUiState: LoginUiState
        get() = loginUiState.value

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent>
        get() = _event.asSharedFlow()


    val onClickListener = object : LoginOnClickListener {
        override fun onUsernameChanged(username: String) {
            _loginUiState.update { it.copy(username = username) }
        }

        override fun onPasswordChanged(password: String) {
            _loginUiState.update { it.copy(password = password) }
        }

        override fun onLoginButtonClicked() {

        }
    }

    private suspend fun loginUserWithEmail(
        username: String,
        password: String
    ) = viewModelScope.launch {
        val loginResult = userRepository.loginWithEmailAndPassword(
            email = currentUiState.username.trim(),
            password = currentUiState.password.trim()
        )

    }

}