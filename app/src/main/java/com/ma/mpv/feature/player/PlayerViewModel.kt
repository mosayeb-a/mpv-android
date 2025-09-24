package com.ma.mpv.feature.player

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayerViewModel : ViewModel() {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _position = MutableStateFlow(0L)
    val position: StateFlow<Long> = _position.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    fun updatePlayingState(value: Boolean) {
        _isPlaying.update { value }
    }

    fun updateDuration(value: Long) {
        _duration.update { value }
        _position.update { pos -> pos.coerceIn(0L, value) }
    }

    fun updatePosition(value: Long) {
        val currentDuration = _duration.value
        _position.update { value.coerceIn(0L, currentDuration) }
    }

    fun updateLoadingState(value: Boolean) {
        _isLoading.update { value }
    }
}