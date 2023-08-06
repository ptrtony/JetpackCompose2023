package com.zjq.jetpackcompose2023.workmanager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.math.roundToInt

class PhotoCompressionWork constructor(
    private val appContext: Context,
    private val params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val stringUri = params.inputData.getString(KEY_CONTENT_URI)
            val uri = Uri.parse(stringUri)
            val compressionThresholdInBytes = params.inputData.getLong(KEY_COMPRESSION_THRESHOLD, 0L)
            var quality = 100
            var bytes: ByteArray ?= null
            appContext.contentResolver.openInputStream(uri)?.use { inputStream ->
                bytes = inputStream.readBytes()
            }
            val bitmap: Bitmap = bytes?.let {
                BitmapFactory.decodeByteArray(it, 0, it.size)
            }?: return@withContext Result.failure()
            var outBytes: ByteArray
            do {
                val outputStream = ByteArrayOutputStream()
                outputStream.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                    outBytes = outputStream.toByteArray()
                    quality -= (quality * 0.1).roundToInt()
                }
            } while (outBytes.size > compressionThresholdInBytes && quality > 5)
            val file = File(appContext.cacheDir, "${params.id}.jpg")
            file.writeBytes(outBytes)
            Result.success(workDataOf(
                KEY_RESULT_PATH to file.absoluteFile
            ))
        }
    }

    companion object {
        const val KEY_CONTENT_URI = "KEY_CONTENT_URI"
        const val KEY_COMPRESSION_THRESHOLD = "KEY_COMPRESSION_THRESHOLD"
        const val KEY_RESULT_PATH = "KEY_RESULT_PATH"
    }
}