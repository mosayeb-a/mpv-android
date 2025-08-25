package com.ma.mpv.feature.browser

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ma.mpv.common.rippleClickable
import com.ma.mpv.common.ui.AppHorizontalDivider
import com.ma.mpv.common.ui.Appbar

@Composable
fun FoldersScreen(
    modifier: Modifier = Modifier,
    onFolderClick: (String) -> Unit,
    viewState: FolderState
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column {
                Appbar(
                    title = "Folders",
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "go back",
                                tint = Color.White
                            )
                        }
                    }
                )
                AppHorizontalDivider(alpha = .12f, thickness = 1.3.dp)
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { },
                icon = {
                    Icon(
                        Icons.Rounded.PlayArrow,
                        contentDescription = null
                    )
                },
                text = { Text("Play Last") },
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 3.dp
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = innerPadding
        ) {
            items(viewState.folders) { folder ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .rippleClickable { onFolderClick(folder.name) }
                        .padding(vertical = 9.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = folder.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${folder.videos.size} videos",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.W400,
                        fontSize = 14.sp,
                    )
                }
                AppHorizontalDivider()
            }
        }
        if (viewState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}