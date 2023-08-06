package com.zjq.jetpackcompose2023.workmanager

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import coil.compose.AsyncImage
import com.zjq.jetpackcompose2023.ui.theme.JetpackCompose2023Theme

class PhotoCompressionWorkManagerActivity: ComponentActivity() {

    private lateinit var workManager: WorkManager
    private val viewModel: PhotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workManager = WorkManager.getInstance(applicationContext)
        setContent {
            JetpackCompose2023Theme {
                val workResult = viewModel.workId?.let { workId ->
                    workManager.getWorkInfoByIdLiveData(workId).observeAsState().value
                }
                LaunchedEffect(key1 = workResult?.outputData) {
                    if (workResult?.outputData != null) {
                        val filePath = workResult.outputData.getString(PhotoCompressionWork.KEY_RESULT_PATH)
                        filePath?.let {
                            val bitmap = BitmapFactory.decodeFile(it)
                            viewModel.updateCompressionBitmap(bitmap)
                        }
                    }
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    viewModel.uncompressedUri?.let {
                        Text(text = "Uncompression photo:")
                        AsyncImage(model = it, contentDescription = null)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    viewModel.compressionBitmap?.let {
                        Text(text = "Compression photo:")
                        Image(bitmap = it.asImageBitmap(), contentDescription = null)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM)
        } ?: return
        viewModel.updateCompressionUri(uri)
        val request = OneTimeWorkRequestBuilder<PhotoCompressionWork>()
            .setInputData(
                workDataOf(
                    PhotoCompressionWork.KEY_CONTENT_URI to uri.toString(),
                    PhotoCompressionWork.KEY_COMPRESSION_THRESHOLD to 1024 * 20L
                )
            )
            .setConstraints(
                Constraints(
                requiresStorageNotLow = true
            )
            )
            .build()
        viewModel.updateWorkId(request.id)
        workManager.enqueue(request)
    }
}