package com.ma.mpv.common.ui

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AppHorizontalDivider(alpha: Float = .05f, thickness: Dp = 1.dp , color: Color = MaterialTheme.colorScheme.onSurface) {
    HorizontalDivider(color = color.copy(alpha), thickness = thickness )
}