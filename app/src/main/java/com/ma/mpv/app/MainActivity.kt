package com.ma.mpv.app

import android.Manifest
import android.content.Intent
import android.graphics.Color.TRANSPARENT
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.ma.mpv.common.START_INDEX_KEY
import com.ma.mpv.common.VIDEO_LIST_KEY
import com.ma.mpv.common.ui.theme.MpvTheme
import com.ma.mpv.feature.browser.FoldersViewModel
import com.ma.mpv.feature.player.PlayerActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: FoldersViewModel by viewModel()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                viewModel.refresh()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(TRANSPARENT,TRANSPARENT),
            navigationBarStyle =
                SystemBarStyle.light(
                    scrim = TRANSPARENT,
                    darkScrim = TRANSPARENT
                )
        )

        setContent {
            MpvTheme {
                AppNavigation(viewModel = viewModel)
                val videoList = arrayListOf(
                    "/storage/emulated/0/algorithem/13.mp4",
                    "/storage/emulated/0/algorithem/14.mp4",
                    "/storage/emulated/0/algorithem/15.mp4",
                    "/storage/emulated/0/algorithem/16.mp4",
                    "/storage/emulated/0/algorithem/17.mp4",
                    "/storage/emulated/0/algorithem/طراحی الگوریتم (جلسه ۱۷) رویکرد حریصانه در توسعه الگوری.mp4",
                    "/storage/emulated/0/algorithem/طراحی الگوریتم (جلسه ۱۸) رویکرد عقبگرد در توسعه الگوریت.mp4",
                    "/storage/emulated/0/algorithem/طراحی الگوریتم (جلسه ۱۹) رویکرد عقبگرد در توسعه الگوریت.mp4",
                    "/storage/emulated/0/algorithem/طراحی الگوریتم (جلسه ۲۰) رویکرد عقبگرد در توسعه الگوریت.mp4",
                    "/storage/emulated/0/algorithem/طراحی الگوریتم (جلسه ۲۱) رویکرد شاخه و حد در توسعه الگور.mp4",
                    "/storage/emulated/0/algorithem/طراحی الگوریتم (جلسه ۲۲) رویکرد شاخه و حد در توسعه الگور.mp4"
                )

                val startIndex = 5

                val intent = Intent(this , PlayerActivity::class.java).apply {
                    putStringArrayListExtra(VIDEO_LIST_KEY, videoList)
                    putExtra(START_INDEX_KEY, startIndex)
                }

                this.startActivity(intent)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_VIDEO)
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}
