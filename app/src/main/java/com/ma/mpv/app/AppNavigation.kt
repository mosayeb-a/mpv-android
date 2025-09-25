package com.ma.mpv.app

import android.content.Intent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ma.mpv.common.START_INDEX_KEY
import com.ma.mpv.common.VIDEO_LIST_KEY
import com.ma.mpv.feature.browser.FolderVideosScreen
import com.ma.mpv.feature.browser.FoldersScreen
import com.ma.mpv.feature.browser.FoldersViewModel
import com.ma.mpv.feature.player.PlayerActivity


@Composable
fun AppNavigation(
    viewModel: FoldersViewModel,
    navController: NavHostController = rememberNavController(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    NavHost(
        navController = navController, startDestination = FoldersRoute,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popExitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None }
    ) {
        composable<FoldersRoute> {
            FoldersScreen(
                onFolderClick = { folderName ->
                    viewModel.selectFolder(folderName)
                    navController.navigate(VideosRoute)
                },
                viewState = state
            )
        }
        composable<VideosRoute> {
            FolderVideosScreen(
                onVideoSelected = { video ->
                    val videos = state.selectedFolder?.videos
                    val intent = Intent(navController.context, PlayerActivity::class.java).apply {
                        putStringArrayListExtra(
                            VIDEO_LIST_KEY,
                            ArrayList(videos?.map { it.path } ?: emptyList())
                        )
                        putExtra(START_INDEX_KEY, videos?.indexOf(video) ?: 0)
                    }
                    navController.context.startActivity(intent)
                },
                onBackClick = { navController.navigateUp() },
                folder = state.selectedFolder!!,
            )
        }
    }
}