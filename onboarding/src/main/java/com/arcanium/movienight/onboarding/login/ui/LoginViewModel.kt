package com.arcanium.movienight.onboarding.login.ui

import androidx.lifecycle.viewModelScope
import com.arcanium.movienight.data.user.UserRepository
import com.arcanium.movienight.domain.Resource
import com.arcanium.movienight.presentation.EventViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
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
            loginUserWithEmail()
        }
    }

    private fun loginUserWithEmail() = viewModelScope.launch {
        val loginResult = userRepository.loginWithEmailAndPassword(
            email = currentUiState.username.trim(),
            password = currentUiState.password.trim()
        )
        when (loginResult) {
            is Resource.Success -> {
                updatingLoading(false)
                _event.emitEvent(event = LoginEvent.AuthSuccess)
            }
            is Resource.Failure -> {

            }
        }
    }

//    private fun <T> MutableSharedFlow<T>.emitEvent(event: T) = viewModelScope.launch {
//        withContext(Dispatchers.Main) {
//            this@emitEvent.emit(event)
//        }
//    }

    private fun updatingLoading(state: Boolean) {
        _loginUiState.update {
            it.copy(isLoading = state)
        }
    }

}