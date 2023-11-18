package com.zjq.jetpackcompose2023.camera_ai.presention

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.zjq.jetpackcompose2023.camera_ai.domain.Classification
import com.zjq.jetpackcompose2023.camera_ai.domain.LandmarkClassifier

class LandmarkImageAnalyzer(
    private val classifier: LandmarkClassifier,
    private val onResult: (List<Classification>) -> Unit
): ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        if (frameSkipCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image.toBitmap()
                .centerCrop(321, 321)

            val result = classifier.classify(bitmap, rotationDegrees)
            onResult(result)
        }
        frameSkipCounter++
        image.close()
    }
}