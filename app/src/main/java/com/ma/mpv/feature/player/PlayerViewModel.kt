package com.ma.mpv.feature.player

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ma.mpv.common.START_INDEX_KEY
import com.ma.mpv.common.VIDEO_LIST_KEY
import com.ma.mpv.domain.VideoAspect
import com.ma.mpv.domain.aspectRatios
import `is`.xyz.mpv.MPVLib
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayerViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val videoPaths: List<String> =
        savedStateHandle.get<ArrayList<String>>(VIDEO_LIST_KEY) ?: emptyList()

    private var currentIndex: Int =
        (savedStateHandle.get<Int>(START_INDEX_KEY) ?: 0)
            .coerceIn(0, videoPaths.size - 1)

    private val _currentVideo = MutableStateFlow(videoPaths.getOrNull(currentIndex))
    val currentVideo: StateFlow<String?> = _currentVideo.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _position = MutableStateFlow(0L)
    val position: StateFlow<Long> = _position.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _controlsShown = MutableStateFlow(true)
    val controlsShown: StateFlow<Boolean> = _controlsShown.asStateFlow()

    private val _currentAspect = MutableStateFlow(VideoAspect.Original)
    val currentAspect: StateFlow<VideoAspect> = _currentAspect.asStateFlow()

    private val _isLocked = MutableStateFlow(false)
    val isLocked: StateFlow<Boolean> = _isLocked.asStateFlow()

    private var aspectRatioIndex = 0

    fun cycleAspectRatio(screenAspectRatio: Double) {
        aspectRatioIndex = (aspectRatioIndex + 1) % aspectRatios.size
        val newAspect = aspectRatios[aspectRatioIndex]
        _currentAspect.value = newAspect
        changeVideoAspect(newAspect, screenAspectRatio)
    }

    fun toggleLock() = _isLocked.update { !it }

    private fun changeVideoAspect(aspect: VideoAspect, screenAspectRatio: Double) {
        var ratio = -1.0
        var pan: Double
        when (aspect) {
            VideoAspect.Crop -> {
                pan = 1.0
                MPVLib.setPropertyDouble("video-zoom", 0.0)
            }

            VideoAspect.Fit -> {
                pan = 0.0
                MPVLib.setPropertyDouble("panscan", 0.0)
            }

            VideoAspect.Stretch -> {
                ratio = screenAspectRatio
                pan = 0.0
            }

            VideoAspect.Original -> {
                ratio = 0.0
                pan = 0.0
            }
        }
        MPVLib.setPropertyDouble("panscan", pan)
        MPVLib.setPropertyDouble("video-aspect-override", ratio)
    }

    fun showControls() = _controlsShown.update { true }
    fun hideControls() = _controlsShown.update { false }
    fun updatePlayingState(value: Boolean) = _isPlaying.update { value }

    fun updateDuration(value: Long) {
        _duration.update { value }
        _position.update { pos -> pos.coerceIn(0L, value) }
    }

    fun updatePosition(value: Long) {
        _position.update { value.coerceIn(0L, _duration.value) }
    }

    fun updateLoadingState(value: Boolean) = _isLoading.update { value }

    fun playPrevious() {
        if (currentIndex > 0) {
            currentIndex--
            _currentVideo.update { videoPaths[currentIndex] }
            resetPlaybackState()
        }
    }

    fun playNext() {
        if (currentIndex < videoPaths.lastIndex) {
            currentIndex++
            _currentVideo.update { videoPaths[currentIndex] }
            resetPlaybackState()
        }
    }

    private fun resetPlaybackState() {
        _isLoading.update { true }
        _position.update { 0L }
        _duration.update { 0L }
    }
}