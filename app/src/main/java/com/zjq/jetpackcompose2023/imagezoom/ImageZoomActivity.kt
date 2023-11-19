package com.zjq.jetpackcompose2023.imagezoom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.zjq.jetpackcompose2023.R
import com.zjq.jetpackcompose2023.ui.theme.JetpackCompose2023Theme

class ImageZoomActivity: ComponentActivity() {

    @SuppressLint("UnusedBoxWithConstraintsScope")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackCompose2023Theme {
                var scale by remember {
                    mutableStateOf(1f)
                }
                var rotation by remember {
                    mutableStateOf(1f)
                }

                var offset by remember {
                    mutableStateOf(Offset.Zero)
                }
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1280f)
                ) {
                    val state = rememberTransformableState {
                        zoomChange, panChange, rotationChange ->
                        scale = (scale * zoomChange).coerceIn(1f, 5f)
                        rotation += rotationChange
                        val extraWidth = (scale - 1) * constraints.maxWidth
                        val extraHeight = (scale - 1) * constraints.maxHeight
                        val maxX = extraWidth / 2f
                        val maxY = extraHeight / 2f
                        offset = Offset(
                            x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                            y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY)
                        )
                    }
                    Image(
                        painter = painterResource(
                            id = R.drawable.ic_launcher_background
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                rotationZ = rotation
                                translationX = offset.x
                                translationY = offset.y
                            }
                            .transformable(state)
                    )
                }
            }
        }
    }
}