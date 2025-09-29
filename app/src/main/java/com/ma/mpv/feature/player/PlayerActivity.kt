package com.ma.mpv.feature.player

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ma.mpv.common.ui.theme.Black
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
                Black.copy(alpha = 0.5f).toArgb()
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
                val currentAspect by viewModel.currentAspect.collectAsStateWithLifecycle()
                val isLocked by viewModel.isLocked.collectAsStateWithLifecycle()
                val currentSpeed by viewModel.playbackSpeed.collectAsStateWithLifecycle()
                val isMuted by viewModel.isMuted.collectAsStateWithLifecycle()

                LaunchedEffect(controlsShown) {
                    val insetsController =
                        WindowCompat.getInsetsController(window, window.decorView)

                    if (controlsShown) {
                        insetsController.show(WindowInsetsCompat.Type.systemBars())
                        insetsController.systemBarsBehavior =
                            WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
                    } else {
                        insetsController.hide(WindowInsetsCompat.Type.systemBars())
                        insetsController.systemBarsBehavior =
                            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    }
                }

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
                    isLocked = isLocked,
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
                    onBack = { finish() },
                    onRotationClick = { cycleOrientation() },
                    onAspectRatioClick = { viewModel.cycleAspectRatio(getScreenAspectRatio()) },
                    onLockClick = { viewModel.toggleLock() },
                    onSpeedChange = { speed ->
                        MPVLib.setPropertyDouble("speed", speed.toDouble())
                        viewModel.updatePlaybackSpeed(speed)
                    },
                    currentSpeed = currentSpeed,
                    aspectRatio = currentAspect,
                    isMuted = isMuted,
                    onMuteClick = { viewModel.toggleMute() }
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

    private fun cycleOrientation() {
        requestedOrientation =
            if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)
                ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
            else
                ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
    }

    fun getScreenAspectRatio(): Double {
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(dm)
        return dm.widthPixels / dm.heightPixels.toDouble()
    }

    override fun eventProperty(property: String, value: Boolean) {
        when (property) {
            "pause" -> viewModel.updatePlayingState(!value)
            "paused-for-cache" -> viewModel.updateLoadingState(value)
            "seeking" -> viewModel.updateLoadingState(value)
            "mute" -> viewModel.setMute(value)
        }
    }

    override fun eventProperty(property: String, value: Long) {
        when (property) {
            "time-pos" -> viewModel.updatePosition(value * 1000)
            "duration/full" -> viewModel.updateDuration(value * 1000)
        }
    }

    override fun eventProperty(property: String, value: Double) {
        when (property) {
            "duration/full" -> viewModel.updateDuration((value * 1000).toLong())
            "speed" -> viewModel.updatePlaybackSpeed(value.toFloat())
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