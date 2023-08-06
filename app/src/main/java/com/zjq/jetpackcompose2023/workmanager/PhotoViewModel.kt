package com.zjq.jetpackcompose2023.workmanager

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.UUID

class PhotoViewModel : ViewModel() {

    var uncompressedUri: Uri? by mutableStateOf(null)
        private set
    var compressionBitmap: Bitmap? by mutableStateOf(null)
        private set
    var workId: UUID? by mutableStateOf(null)
        private set

    fun updateCompressionUri(uri: Uri?) {
        uncompressedUri = uri
    }

    fun updateCompressionBitmap(bitmap: Bitmap?) {
        compressionBitmap = bitmap
    }

    fun updateWorkId(id: UUID?) {
        workId = id
    }
}