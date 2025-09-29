package com.ma.mpv.feature.player.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.ma.mpv.common.ui.playerRippleConfiguration
import com.ma.mpv.common.ui.theme.Black
import com.ma.mpv.domain.VideoAspect
import kotlinx.coroutines.delay

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
    onSpeedChange: (Float) -> Unit,
    currentSpeed: Float = 1.0f,
    aspectRatio: VideoAspect,
    isMuted: Boolean,
    onMuteClick: () -> Unit,
    onScreenshotClick: () -> Unit,
    doubleTapSeekAmount: Int,
    isSeekingForwards: Boolean,
    onLeftDoubleTap: () -> Unit,
    onRightDoubleTap: () -> Unit,
    onCenterDoubleTap: () -> Unit,
    onUpdateSeekAmount: (Int) -> Unit
) {
    var showLock by remember { mutableStateOf(isLocked) }
    var showSpeedOverlay by remember { mutableStateOf(false) }
    var isDoubleTapSeeking by remember { mutableStateOf(false) }

    LaunchedEffect(doubleTapSeekAmount) {
        if (doubleTapSeekAmount != 0) {
            isDoubleTapSeeking = true
            delay(800)
            isDoubleTapSeeking = false
            onUpdateSeekAmount(0)
        }
    }

    CompositionLocalProvider(LocalRippleConfiguration provides playerRippleConfiguration) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (isLocked) {
                                showLock = true
                            } else {
                                if (showSpeedOverlay) {
                                    showSpeedOverlay = false
                                    if (!controlsShown) {
                                        onToggleControls()
                                    }
                                } else {
                                    onToggleControls()
                                }
                            }
                        },
                        onDoubleTap = { offset ->
                            if (isLocked || isDoubleTapSeeking) return@detectTapGestures
                            val x = offset.x
                            val width = size.width.toFloat()
                            if (x < width * 2 / 5) {
                                onLeftDoubleTap()
                            } else if (x > width * 3 / 5) {
                                onRightDoubleTap()
                            } else {
                                onCenterDoubleTap()
                            }
                        }
                    )
                }
        ) {
            AspectRatioOverlay(aspectRatio = aspectRatio)

            AnimatedVisibility(
                visible = showSpeedOverlay && !isLocked,
                enter = fadeIn(animationSpec = tween(200)),
                exit = fadeOut(animationSpec = tween(200))
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    SpeedOverlay(
                        modifier = Modifier.pointerInput(Unit) {},
                        onSpeedChange = onSpeedChange,
                        initialSpeed = currentSpeed
                    )
                }
            }

            AnimatedVisibility(
                visible = controlsShown && !isLocked && !showSpeedOverlay,
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
                        onRotationClick = onRotationClick,
                        onMuteClick = onMuteClick,
                        onSpeedClick = { showSpeedOverlay = true },
                        speed = currentSpeed,
                        isMuted = isMuted,
                        onScreenshotClick = onScreenshotClick,
                    )
                }
            }

            AnimatedVisibility(
                visible = controlsShown && !isLocked && !showSpeedOverlay,
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

            DoubleTapSeekOverlay(
                doubleTapSeekAmount = doubleTapSeekAmount,
                isSeekingForwards = isSeekingForwards
            )
        }
    }
}