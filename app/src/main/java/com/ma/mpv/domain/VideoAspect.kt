package com.ma.mpv.domain

enum class VideoAspect {
    Crop, Fit, Stretch, Original
}

val aspectRatios =
    listOf(VideoAspect.Original, VideoAspect.Fit, VideoAspect.Stretch, VideoAspect.Crop)