package com.ma.mpv.feature.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AspectRatio
import androidx.compose.material.icons.rounded.CropLandscape
import androidx.compose.material.icons.rounded.FitScreen
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ma.mpv.common.ui.theme.Black
import com.ma.mpv.domain.VideoAspect

@Composable
fun BottomControls(
    isPlaying: Boolean,
    onPlayPauseToggle: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onLockClick: () -> Unit,
    onAspectRatioClick: () -> Unit,
    modifier: Modifier = Modifier,
    aspectRatio: VideoAspect
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Black.copy(alpha = 0.7f))
                .clickable { onLockClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Rounded.Lock,
                contentDescription = "Lock",
                tint = Color.White
            )
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Black.copy(alpha = 0.7f))
                    .clickable { onPrevious() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.SkipPrevious,
                    contentDescription = "Previous Video",
                    tint = Color.White
                )
            }

            PlayPauseButton(
                isPlaying = isPlaying,
                onPlayPauseToggle = onPlayPauseToggle,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Black.copy(alpha = 0.7f))
                    .clickable { onNext() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.SkipNext,
                    contentDescription = "Next Video",
                    tint = Color.White
                )
            }
        }

        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Black.copy(alpha = 0.7f))
                .clickable { onAspectRatioClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = when (aspectRatio) {
                    VideoAspect.Original -> Icons.Rounded.AspectRatio
                    VideoAspect.Crop -> Icons.Rounded.CropLandscape
                    VideoAspect.Fit -> Icons.Rounded.FitScreen
                    VideoAspect.Stretch -> Icons.Rounded.Fullscreen
                },
                contentDescription = "Aspect Ratio",
                tint = Color.White
            )
        }
    }
}