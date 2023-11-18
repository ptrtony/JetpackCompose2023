package com.zjq.jetpackcompose2023.camera_ai.domain

import android.graphics.Bitmap

interface LandmarkClassifier {
    fun classify(bitmap: Bitmap, orientation: Int): List<Classification>
}