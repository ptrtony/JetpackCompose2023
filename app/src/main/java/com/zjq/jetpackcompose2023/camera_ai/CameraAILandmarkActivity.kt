package com.zjq.jetpackcompose2023.camera_ai

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.view.CameraController.IMAGE_ANALYSIS
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.zjq.jetpackcompose2023.camera_ai.data.TfLiteLandmarkClassifier
import com.zjq.jetpackcompose2023.camera_ai.domain.Classification
import com.zjq.jetpackcompose2023.camera_ai.presention.CameraPreview
import com.zjq.jetpackcompose2023.camera_ai.presention.LandmarkImageAnalyzer
import com.zjq.jetpackcompose2023.ui.theme.JetpackCompose2023Theme

class CameraAILandmarkActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasCameraPermission()) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
        }
        setContent {
            JetpackCompose2023Theme {
                var classifications by remember {
                    mutableStateOf(emptyList<Classification>())
                }

                val analyzer = remember {
                   LandmarkImageAnalyzer(
                       classifier = TfLiteLandmarkClassifier(
                           context = applicationContext),
                       onResult = {
                           classifications = it
                       }
                   )
                }
                val controller = remember {
                    LifecycleCameraController(applicationContext).apply {
                        setEnabledUseCases(IMAGE_ANALYSIS)
                        setImageAnalysisAnalyzer(ContextCompat.getMainExecutor(applicationContext), analyzer)
                    }
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    CameraPreview(controller = controller, Modifier.fillMaxSize())
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)) {
                        classifications.forEach {
                            Text(
                                text = it.name,
                                modifier = Modifier.fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .padding(8.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }
}