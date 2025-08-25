package com.ma.mpv.app


import android.app.Application
import com.ma.mpv.feature.browser.FoldersViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val appModule = module {
            viewModel { FoldersViewModel(contentResolver) }
        }

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}
