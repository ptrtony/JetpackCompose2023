package com.zjq.jetpackcompose2023.uri

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.core.net.toUri
import com.zjq.jetpackcompose2023.ui.theme.JetpackCompose2023Theme
import java.io.File
import java.io.FileOutputStream

class UriDemoActivity: ComponentActivity() {

    companion object {
        private const val TAG = "UriDemoActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri = Uri.parse("android.resource://$packageName/drawable/ic_launcher_background.xml")
        val icBytes = contentResolver.openInputStream(uri).use {
            it?.readBytes()
        }
        Log.d(TAG, "bytes size:${icBytes?.size}")
        val file = File(filesDir, "kermit.jpg")
        FileOutputStream(file).use {
            it.write(icBytes)
        }
        Log.d(TAG, "file path:${file.toUri()}")
        setContent {
            JetpackCompose2023Theme {
                val pickImage = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(), onResult = { contentUri ->
                        Log.d(TAG, contentUri.toString())
                    }
                )
                Button(onClick = {
                    pickImage.launch("image/*")
                }) {
                    Text(text = "Pick image")
                }
            }
        }
    }
}