package com.ma.mpv.feature.player.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ma.mpv.common.ui.theme.Black
import com.ma.mpv.domain.VideoAspect

@Composable
fun PlayerControllers(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    duration: Long,
    position: Long,
    isLoading: Boolean,
    controlsShown: Boolean,
    isLocked: Boolean,
    onPlayPauseToggle: () -> Unit,
    onSeekTo: (Float) -> Unit,
    onToggleControls: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    currentVideo: String?,
    onBack: () -> Unit,
    onRotationClick: () -> Unit,
    onAspectRatioClick: () -> Unit,
    onLockClick: () -> Unit,
    aspectRatio: VideoAspect,
) {
    var showLock by remember { mutableStateOf(isLocked) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                enabled = true,
                onClick = {
                    if (isLocked) {
                        showLock = true
                    } else {
                        onToggleControls()
                    }
                }
            )
    ) {
        AspectRatioOverlay(aspectRatio = aspectRatio)

        AnimatedVisibility(
            visible = controlsShown && !isLocked,
            enter = fadeIn(animationSpec = tween(200)),
            exit = fadeOut(animationSpec = tween(200))
        ) {
            Column(modifier = Modifier.align(Alignment.TopCenter)) {
                TopBarControllers(
                    currentVideoPath = currentVideo,
                    onBack = onBack
                )
                Spacer(Modifier.height(22.dp))

                PlayerActions(
                    onRotationClick = onRotationClick
                )
            }
        }

        AnimatedVisibility(
            visible = controlsShown && !isLocked,
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
                        .padding(horizontal = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SeekBarWithDuration(
                        duration = duration,
                        position = position,
                        onSeekTo = onSeekTo,
                        modifier = Modifier.fillMaxWidth()
                    )
                    BottomControls(
                        isPlaying = isPlaying,
                        onPlayPauseToggle = onPlayPauseToggle,
                        onPrevious = onPrevious,
                        onNext = onNext,
                        onAspectRatioClick = onAspectRatioClick,
                        onLockClick = onLockClick,
                        aspectRatio = aspectRatio
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = showLock && isLocked,
            enter = fadeIn(animationSpec = tween(200)),
            exit = fadeOut(animationSpec = tween(200))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LockControl(
                    isLocked = isLocked,
                    onShowLockChange = { showLock = it },
                    onLockClick = onLockClick
                )
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