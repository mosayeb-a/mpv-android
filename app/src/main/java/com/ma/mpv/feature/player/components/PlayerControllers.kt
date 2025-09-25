package com.ma.mpv.feature.player.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ma.mpv.R

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
    onToggleControls: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable { onToggleControls() }
            .padding(16.dp)
    ) {
        AnimatedVisibility(
            visible = controlsShown,
            enter = fadeIn(animationSpec = tween(200)),
            exit = fadeOut(animationSpec = tween(200))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                SeekBarWithDuration(
                    duration = duration,
                    position = position,
                    onSeekTo = onSeekTo,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }
        }

        AnimatedVisibility(
            visible = controlsShown && !isLoading,
            enter = fadeIn(animationSpec = tween(200)),
            exit = fadeOut(animationSpec = tween(200))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onPrevious) {
                        Icon(
                            imageVector = Icons.Rounded.SkipPrevious,
                            contentDescription = "Previous Video",
                            tint = androidx.compose.ui.graphics.Color.White
                        )
                    }
                    PlayPauseButton(
                        isPlaying = isPlaying,
                        onPlayPauseToggle = onPlayPauseToggle,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    IconButton(onClick = onNext) {
                        Icon(
                            imageVector = Icons.Rounded.SkipNext,
                            contentDescription = "Next Video",
                            tint = androidx.compose.ui.graphics.Color.White
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(animationSpec = tween(150)),
            exit = fadeOut(animationSpec = tween(150))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator()
            }
        }
    }
}