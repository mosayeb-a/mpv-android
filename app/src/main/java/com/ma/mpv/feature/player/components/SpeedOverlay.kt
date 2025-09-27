package com.ma.mpv.feature.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ma.mpv.common.ui.theme.Black
import java.util.Locale

@Composable
fun SpeedOverlay(
    modifier: Modifier = Modifier,
    onSpeedChange: (Float) -> Unit,
    initialSpeed: Float = 1.0f
) {
    var speed by remember { mutableFloatStateOf(initialSpeed) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Black.copy(alpha = 0.5f))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActionButton(
                icon = {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Rounded.Remove,
                        contentDescription = "minus",
                        tint = White
                    )
                },
                label = "",
                onClick = {
                    if (speed > 0.25f) {
                        val newSpeed = (speed - 0.5f).coerceAtLeast(0.25f)
                        speed = newSpeed
                        onSpeedChange(newSpeed)
                    }
                }
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = String.format(Locale.US, "%.2fx", speed),
                    color = White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700
                )
            }

            ActionButton(
                icon = {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "plus",
                        tint = White
                    )
                },
                label = "",
                onClick = {
                    if (speed < 4.0f) {
                        val newSpeed = (speed + 0.5f).coerceAtMost(4.0f)
                        speed = newSpeed
                        onSpeedChange(newSpeed)
                    }
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpeedSlider(
                speed = speed,
                onSpeedChange = { newSpeed ->
                    speed = newSpeed
                    onSpeedChange(newSpeed)
                },
                modifier = Modifier.weight(1f)
            )

            ActionButton(
                icon = {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Rounded.Replay,
                        contentDescription = "Rest",
                        tint = White
                    )
                },
                label = "",
                onClick = {
                    speed = 1.0f
                    onSpeedChange(1.0f)
                }
            )
        }
    }
}