package com.ma.mpv.feature.player.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ma.mpv.common.ui.theme.Black

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
    onNext: () -> Unit,
    currentVideo: String?,
    onBack: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable { onToggleControls() }
    ) {
        AnimatedVisibility(
            visible = controlsShown,
            enter = fadeIn(animationSpec = tween(200)),
            exit = fadeOut(animationSpec = tween(200))
        ) {
            TopBarControllers(
                modifier = Modifier.align(Alignment.TopCenter),
                currentVideoPath = currentVideo,
                onBack = onBack
            )
        }

        AnimatedVisibility(
            visible = controlsShown,
            enter = fadeIn(animationSpec = tween(200)),
            exit = fadeOut(animationSpec = tween(200))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(Black.copy(alpha = 0.5f))
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SeekBarWithDuration(
                        duration = duration,
                        position = position,
                        onSeekTo = onSeekTo,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    PlaybackButtons(
                        isPlaying = isPlaying,
                        onPlayPauseToggle = onPlayPauseToggle,
                        onPrevious = onPrevious,
                        onNext = onNext
                    )
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