package com.ma.mpv.feature.browser

import android.content.ContentResolver
import android.content.ContentUris
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma.mpv.common.observe
import com.ma.mpv.domain.Video
import com.ma.mpv.domain.VideoFolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

data class FolderState(
    val isLoading: Boolean = true,
    val folders: List<VideoFolder> = emptyList(),
    val selectedFolder: VideoFolder? = null,
)

class FoldersViewModel(
    private val contentResolver: ContentResolver,
) : ViewModel() {

    private val _state = MutableStateFlow(FolderState())
    val state = _state.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            contentResolver.videos().collect { folders ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        folders = folders
                    )
                }
            }
        }
    }

    fun selectFolder(folderName: String) {
        _state.update { state ->
            state.copy(
                selectedFolder = state.folders.find { it.name == folderName }
            )
        }
    }

    private suspend fun getVideos(): List<VideoFolder> = withContext(Dispatchers.IO) {
        val videoMap = mutableMapOf<String, MutableList<Video>>()
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE
        )

        contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val path = cursor.getString(pathColumn)
                val duration = cursor.getLong(durationColumn)
                val size = cursor.getLong(sizeColumn)

                val uri =
                    ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)

                val file = File(path)
                if (file.exists()) {
                    val folderName =
                        if (file.parentFile?.name == "0") "Internal Memory" else file.parentFile?.name ?: "Unknown"
                    val video = Video(name, path, uri, duration, size)
                    videoMap.getOrPut(folderName) { mutableListOf() }.add(video)
                }
            }
        }

        videoMap.map { (folderName, videos) -> VideoFolder(folderName, videos) }
            .sortedBy { it.name }
    }

    private fun ContentResolver.videos(): Flow<List<VideoFolder>> =
        observe(MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            .map { getVideos() }
            .flowOn(Dispatchers.IO)
}