package com.ma.mpv.feature.player.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.ma.mpv.common.ui.theme.Black
import kotlin.math.abs

@Composable
fun DoubleTapSeekOverlay(
    doubleTapSeekAmount: Int,
    isSeekingForwards: Boolean,
) {
    val alpha by animateFloatAsState(
        targetValue = if (doubleTapSeekAmount != 0) 0.2f else 0f,
        label = "double_tap_alpha"
    )

    if (doubleTapSeekAmount != 0) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = if (isSeekingForwards) Alignment.CenterEnd else Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.4f)
                    .background(Color.White.copy(alpha = alpha))
            ) {
                Text(
                    text = "${abs(doubleTapSeekAmount)}s",
                    color = Black,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}