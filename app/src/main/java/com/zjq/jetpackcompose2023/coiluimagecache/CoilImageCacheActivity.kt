package com.zjq.jetpackcompose2023.coiluimagecache

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.zjq.jetpackcompose2023.ui.theme.JetpackCompose2023Theme

class CoilImageCacheActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackCompose2023Theme {
                val imageUrl = "https://i1.huishahe.com/uploads/tu/202204/3/d4116f6117%20(1).jpg"
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1280f/847f)
                )
            }
        }
    }
}