package com.zjq.jetpackcompose2023.cleancodehacks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

class CleanCodeHacksActivity: ComponentActivity() {

//    private val sharedPreferences by lazy {
//        getSharedPreferences("my_prefs", MODE_PRIVATE)
//    }
    private var token by sharedPreference("token")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        sharedPreferences.edit().putString("token", "Hello world").apply()
//        val token = sharedPreferences.getString("token", "")
        token = "hello world"
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(text = token,
                    modifier = Modifier
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}