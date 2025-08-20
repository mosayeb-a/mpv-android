package com.ma.sway.domain

import android.net.Uri

data class Video(
    val name: String,
    val path: String,
    val uri: Uri,
    val duration: Long,
    val size: Long,
)