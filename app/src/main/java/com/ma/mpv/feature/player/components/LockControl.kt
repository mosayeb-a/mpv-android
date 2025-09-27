package com.ma.mpv.feature.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ma.mpv.common.ui.theme.Black
import kotlinx.coroutines.delay

@Composable
fun LockControl(
    modifier: Modifier = Modifier,
    isLocked: Boolean,
    onShowLockChange: (Boolean) -> Unit,
    onLockClick: () -> Unit,
) {
    LaunchedEffect(isLocked) {
        if (isLocked) {
            onShowLockChange(true)
            delay(2_500)
            onShowLockChange(false)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top)),
        contentAlignment = Alignment.TopEnd
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .size(42.dp)
                .clip(CircleShape)
                .background(Black.copy(alpha = 0.7f))
                .clickable { onLockClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(22.dp),
                imageVector = Icons.Rounded.Lock,
                contentDescription = "Lock",
                tint = Color.White
            )
        }
    }
}