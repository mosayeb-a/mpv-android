package com.ma.mpv.feature.player.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ma.mpv.common.formatTime

@Composable
fun PlayerControllers(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    duration: Long,
    position: Long,
    isLoading: Boolean,
    controlsShown: Boolean,
    onPlayPauseToggle: () -> Unit,
    onSeekTo: (Float) -> Unit,
    onToggleControls: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable { onToggleControls() }
            .padding(16.dp)
    ) {
        AnimatedVisibility(
            visible = controlsShown,
            enter = fadeIn(animationSpec = tween(durationMillis = 200)),
            exit = fadeOut(animationSpec = tween(durationMillis = 200))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Button(
                    onClick = onPlayPauseToggle,
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    Text(text = if (isPlaying) "Pause" else "Play")
                }

                Text(
                    text = formatTime(position) + " / " + formatTime(duration),
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp)
                )

                Slider(
                    value = if (duration > 0) position.toFloat() / duration.toFloat() else 0f,
                    onValueChange = onSeekTo,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                )
            }
        }
        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(animationSpec = tween(durationMillis = 150)),
            exit = fadeOut(animationSpec = tween(durationMillis = 150))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(48.dp),
                    color = Color.White
                )
            }
        }
    }
}