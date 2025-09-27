package com.ma.mpv.feature.player.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ma.mpv.common.ui.theme.Black
import com.ma.mpv.domain.VideoAspect
import kotlinx.coroutines.delay


@Composable
fun AspectRatioOverlay(
    aspectRatio: VideoAspect
) {
    var show by remember { mutableStateOf(false) }

    LaunchedEffect(aspectRatio) {
        show = true
        delay(200)
        show = false
    }

    AnimatedVisibility(
        visible = show,
        enter = fadeIn(animationSpec = tween(100)),
        exit = fadeOut(animationSpec = tween(100))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Black.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = aspectRatio.name,
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.9f),
                        offset = Offset(2f, 2f),
                        blurRadius = 5f
                    )
                )
            )
        }
    }
}
