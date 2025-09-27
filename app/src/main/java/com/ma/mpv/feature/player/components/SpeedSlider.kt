package com.ma.mpv.feature.player.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun SpeedSlider(
    modifier: Modifier = Modifier,
    speed: Float,
    onSpeedChange: (Float) -> Unit
) {
    val min = 0.25f
    val max = 4.0f
    val speedSteps = listOf(0.25f, 1.0f, 2.0f, 3.0f, 4.0f)

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            value = speed,
            onValueChange = { newValue -> onSpeedChange(newValue) },
            valueRange = min..max,
            steps = 0,
            modifier = Modifier
                .fillMaxWidth()
                .scale(1f, 0.8f),
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = White.copy(alpha = 0.3f)
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            speedSteps.forEach { step ->
                Text(
                    text = String.format(Locale.US, "%.2fx", step),
                    style = MaterialTheme.typography.labelSmall,
                    color = White,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            onSpeedChange(step)
                        }
                        .padding(2.dp)
                )
            }
        }
    }
}
