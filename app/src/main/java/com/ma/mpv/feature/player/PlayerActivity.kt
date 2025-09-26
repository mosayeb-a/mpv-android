package com.ma.mpv.feature.player

import android.os.Bundle
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ma.mpv.common.ui.theme.MpvTheme
import com.ma.mpv.databinding.PlayerLayoutBinding
import com.ma.mpv.feature.player.components.PlayerControllers
import `is`.xyz.mpv.MPVLib
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerActivity : AppCompatActivity(), MPVLib.EventObserver {
    private val binding by lazy { PlayerLayoutBinding.inflate(layoutInflater) }
    private val player by lazy { binding.player }
    private val viewModel: PlayerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.dark(
                android.graphics.Color.TRANSPARENT
            )
        )
        setContentView(binding.root)

        player.initialize(filesDir.path, cacheDir.path)
        player.addObserver(this)

        binding.controls.setContent {
            MpvTheme {
                val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
                val duration by viewModel.duration.collectAsStateWithLifecycle()
                val position by viewModel.position.collectAsStateWithLifecycle()
                val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
                val controlsShown by viewModel.controlsShown.collectAsStateWithLifecycle()
                val currentVideo by viewModel.currentVideo.collectAsStateWithLifecycle()

                LaunchedEffect(currentVideo) {
                    currentVideo?.let { path ->
                        MPVLib.command(arrayOf("loadfile", path, "replace"))
                    }
                }

                PlayerControllers(
                    modifier = Modifier.navigationBarsPadding(),
                    isPlaying = isPlaying,
                    duration = duration,
                    position = position,
                    isLoading = isLoading,
                    controlsShown = controlsShown,
                    onPlayPauseToggle = { togglePlayPause() },
                    onSeekTo = { progress ->
                        val newPosition = (progress * duration).toLong()
                        seekTo(newPosition)
                        viewModel.updatePosition(newPosition)
                    },
                    onToggleControls = {
                        if (controlsShown) viewModel.hideControls() else viewModel.showControls()
                    },
                    onNext = { viewModel.playNext() },
                    onPrevious = { viewModel.playPrevious() },
                    currentVideo = currentVideo,
                    onBack = { finish() }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.destroy()
        player.removeObserver(this)
    }

    private fun togglePlayPause() {
        player.cyclePause()
    }

    private fun seekTo(positionMs: Long) {
        player.timePos = positionMs / 1000.0
    }

    override fun eventProperty(property: String, value: Boolean) {
        when (property) {
            "pause" -> viewModel.updatePlayingState(!value)
            "paused-for-cache" -> viewModel.updateLoadingState(value)
            "seeking" -> viewModel.updateLoadingState(value)
        }
    }

    override fun eventProperty(property: String, value: Long) {
        when (property) {
            "time-pos" -> viewModel.updatePosition(value * 1000)
            "duration/full" -> viewModel.updateDuration(value * 1000)
        }
    }

    override fun eventProperty(property: String, value: Double) {
        if (property == "duration/full") {
            viewModel.updateDuration((value * 1000).toLong())
        }
    }

    override fun eventProperty(property: String, value: String) {}
    override fun eventProperty(property: String) {}

    override fun event(eventId: Int) {
        if (eventId == MPVLib.mpvEventId.MPV_EVENT_SHUTDOWN) {
            viewModel.updateLoadingState(false)
            finish()
        }
    }

    override fun efEvent(err: String?) {
        err?.let {
            viewModel.updateLoadingState(false)
            Toast.makeText(this, "error: $it", Toast.LENGTH_SHORT).show()
        }
    }
}