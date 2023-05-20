package com.arcanium.movienight.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class EventViewModel : ViewModel() {
    protected fun <T> MutableSharedFlow<T>.emitEvent(event: T) = viewModelScope.launch {
        withContext(Dispatchers.Main) {
            this@emitEvent.emit(event)
        }
    }
}