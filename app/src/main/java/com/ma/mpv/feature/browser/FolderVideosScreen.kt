package com.ma.mpv.feature.browser

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ma.mpv.common.formatDuration
import com.ma.mpv.common.formatFileSize
import com.ma.mpv.common.rippleClickable
import com.ma.mpv.common.ui.AppHorizontalDivider
import com.ma.mpv.common.ui.Appbar
import com.ma.mpv.common.ui.AsyncThumbImage
import com.ma.mpv.domain.Video
import com.ma.mpv.domain.VideoFolder


@Composable
fun FolderVideosScreen(
    modifier: Modifier = Modifier,
    onVideoSelected: (Video) -> Unit,
    folder: VideoFolder,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column {
                Appbar(
                    title = folder.name,
                    onBackClick = onBackClick,
                )
                AppHorizontalDivider(alpha = 0.12f, thickness = 1.3.dp)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding,
        ) {
            items(folder.videos, key = { it.uri }) { video ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .rippleClickable { onVideoSelected(video) }
                        .padding(horizontal = 16.dp, vertical = 9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncThumbImage(uri = video.uri)

                    Spacer(Modifier.width(8.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = video.name,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 2,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 16.sp,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = formatDuration(video.duration) + " • " + formatFileSize(video.size),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                }
                AppHorizontalDivider()
            }
        }
    }
}