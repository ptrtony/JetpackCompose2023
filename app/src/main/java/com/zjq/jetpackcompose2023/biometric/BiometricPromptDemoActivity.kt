package com.zjq.jetpackcompose2023.biometric

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontVariation

class BiometricPromptDemoActivity: AppCompatActivity() {
    private val biometricPromptManager by lazy {
        BiometricPromptManager(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val biometricResult by biometricPromptManager.promptResults.collectAsState(initial = null)
                val enrollLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult(),
                    onResult = {
                        println("Activity result:$it")
                    }
                )
                LaunchedEffect(biometricResult) {
                    if (biometricResult is BiometricPromptManager.BiometricResult.AuthenticateNotSet) {
                        if (Build.VERSION.SDK_INT >= 30) {
                            val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                                putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                    BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                                )

                            }
                            enrollLauncher.launch(enrollIntent)
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = {
                        biometricPromptManager.showBiometricPrompt(
                            "Sample prompt",
                            "Sample prompt describe"
                        )
                    }) {
                        Text(text = "Authenticate")
                    }
                    biometricResult?.let { result ->
                        Text(
                            text = when (result) {
                                BiometricPromptManager.BiometricResult.AuthenticateNotSet -> {
                                    "Authenticate not set"
                                }
                                is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                                    result.error
                                }
                                BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                                    "Authenticate failed"
                                }
                                BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                                    "Authenticate success"
                                }
                                BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                                    "Feature unavailable"
                                }
                                BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                                    "Hardware unavailable"
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}