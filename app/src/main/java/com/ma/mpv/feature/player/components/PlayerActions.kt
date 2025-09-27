package com.ma.mpv.feature.player.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.VolumeMute
import androidx.compose.material.icons.automirrored.rounded.VolumeOff
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.ScreenRotation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun PlayerActions(
    modifier: Modifier = Modifier,
    onRotationClick: () -> Unit,
    onMuteClick: () -> Unit,
    onSpeedClick: () -> Unit,
    speed: Float,
) {
    LazyRow(
        modifier = modifier
    ) {
        item {
            ActionButton(
                icon = {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.AutoMirrored.Rounded.VolumeMute,
                        contentDescription = "Mute",
                        tint = Color.White
                    )
                },
                label = "Mute",
                onClick = onMuteClick
            )
        }

        item {
            ActionButton(
                icon = {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Rounded.CameraAlt,
                        contentDescription = "Screenshot",
                        tint = Color.White
                    )
                },
                label = "Screenshot",
                onClick = { }
            )
        }

        item {
            ActionButton(
                icon = {
                    Text(
                        text = if (speed % 1.0f == 0f) {
                            "${speed.toInt()}X"
                        } else {
                            "${String.format(Locale.US, "%.2f", speed)}X"
                        },
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W800
                    )
                },
                label = "Speed",
                onClick = onSpeedClick
            )
        }

        item {
            ActionButton(
                icon = {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Rounded.ScreenRotation,
                        contentDescription = "Screen Rotation",
                        tint = Color.White
                    )
                },
                label = "Screen Rotation",
                onClick = onRotationClick
            )
        }
    }
}

