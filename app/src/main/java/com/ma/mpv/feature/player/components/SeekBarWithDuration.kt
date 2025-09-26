package com.ma.mpv.feature.player.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import `is`.xyz.mpv.Utils.prettyTime

@Composable
fun SeekBarWithDuration(
    duration: Long,
    position: Long,
    onSeekTo: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var showRemaining by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(4.dp),
            text = prettyTime((position / 1000).toInt()),
            color = Color.White,
            style = MaterialTheme.typography.labelLarge,
        )

        Spacer(Modifier.width(8.dp))

        Slider(
            modifier = Modifier
                .weight(1f)
                .scale(1f, 0.8f),
            value = if (duration > 0) position.toFloat() / duration.toFloat() else 0f,
            onValueChange = onSeekTo,
            valueRange = 0f..1f,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor =  MaterialTheme.colorScheme.primary,
                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
            )
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = if (showRemaining) {
                val remaining = ((duration - position).coerceAtLeast(0L) / 1000).toInt()
                "-${prettyTime(remaining)}"
            } else {
                prettyTime((duration / 1000).toInt())
            },
            color = Color.White,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .padding(4.dp)
                .clickable { showRemaining = !showRemaining }
        )
    }
}