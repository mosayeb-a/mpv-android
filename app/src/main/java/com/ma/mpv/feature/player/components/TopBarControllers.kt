package com.ma.mpv.feature.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material.icons.rounded.Subtitles
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ma.mpv.common.ui.theme.Black
import java.io.File

@Composable
fun TopBarControllers(
    modifier: Modifier = Modifier,
    currentVideoPath: String?,
    onBack: () -> Unit,
    onSubtitles: () -> Unit = {},
    onMusic: () -> Unit = {},
    onMore: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Black.copy(alpha = 0.5f))
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top))
            .height(56.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TopBarIcon(
            icon = Icons.AutoMirrored.Rounded.ArrowBack,
            description = "Back",
            onClick = onBack
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            text = currentVideoPath?.let { File(it).name } ?: "",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TopBarIcon(Icons.Rounded.Subtitles, "Subtitles", onClick = onSubtitles)
            TopBarIcon(Icons.Rounded.MusicNote, "Audio", onClick = onMusic)
            TopBarIcon(Icons.Rounded.MoreVert, "More", onClick = onMore)
        }
    }
}

@Composable
private fun TopBarIcon(
    icon: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    Icon(
        imageVector = icon,
        contentDescription = description,
        tint = Color.White,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(8.dp)
    )
}