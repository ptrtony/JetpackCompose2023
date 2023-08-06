package com.zjq.jetpackcompose2023

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zjq.jetpackcompose2023.ui.theme.JetpackCompose2023Theme
import com.zjq.jetpackcompose2023.workmanager.PhotoCompressionWorkManagerActivity


class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            JetpackCompose2023Theme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { 
                        Intent(this@MainActivity, PhotoCompressionWorkManagerActivity::class.java).let { 
                            startActivity(it)
                        }
                    }) {
                        Text(text = "Android WorkManager")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { 
//                        Intent(Intent.ACTION_MAIN).also {
//                            if (it.resolveActivity(packageManager) != null) {
//                                it.`package` = "com.google.android.youtube"
//                                startActivity(it)
//                            }
//                        }
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_EMAIL, "test@test.com")
                            putExtra(Intent.EXTRA_SUBJECT, "This is my subject")
                            putExtra(Intent.EXTRA_TEXT, "this is my content email")
                        }
                        if (intent.resolveActivity(packageManager) != null) {
                            startActivity(intent)
                        }
                    }) {
                        Text(text = "Filter & Intent Filter")
                    }
                }
            }
        }
    }
    
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackCompose2023Theme {
        Greeting("Android")
    }
}