package com.ma.mpv.feature.player.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
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

@Composable
fun PlayerActions(
    modifier: Modifier = Modifier,
    onRotationClick: () -> Unit
) {
    LazyRow(
        modifier = modifier
    ) {
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
                    Text("1X", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                },
                label = "Speed",
                onClick = {}
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

