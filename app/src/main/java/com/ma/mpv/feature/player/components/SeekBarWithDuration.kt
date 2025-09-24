package com.ma.mpv.feature.player.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ma.mpv.common.formatTime

@Composable
fun SeekBarWithDuration(
    duration: Long,
    position: Long,
    onSeekTo: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Slider(
            modifier = Modifier
                .weight(0.8f)
                .scale(1f, 0.8f),
            value = if (duration > 0) position.toFloat() / duration.toFloat() else 0f,
            onValueChange = onSeekTo,
            valueRange = 0f..1f,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.White.copy(alpha = 0.6f)
            )
        )
        Spacer(Modifier.width(6.dp))

        Text(
            text = "${formatTime(position)} / ${formatTime(duration)}",
            color = Color.White,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}
