package com.ma.mpv.feature.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlayerState(
    val isPlaying: Boolean = false,
    val duration: Long = 0L,
    val position: Long = 0L,
    val isLoading: Boolean = true
)

class PlayerViewModel : ViewModel() {
    private val _playerState = MutableStateFlow(PlayerState())
    val playerState: StateFlow<PlayerState> = _playerState

    fun updateLoadingState(isLoading: Boolean) {
        viewModelScope.launch {
            _playerState.update {
                it.copy(isLoading = isLoading)
            }
        }
    }

    fun updatePlayingState(isPlaying: Boolean) {
        _playerState.update {
            it.copy(isPlaying = isPlaying)
        }
    }

    fun updatePosition(position: Long) {
        _playerState.update {
            it.copy(position = position.coerceIn(0L, it.duration))
        }
    }

    fun updateDuration(duration: Long) {
        _playerState.update {
            it.copy(duration = duration)
        }
    }
}