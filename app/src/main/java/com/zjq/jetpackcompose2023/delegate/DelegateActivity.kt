package com.zjq.jetpackcompose2023.delegate

import android.app.Activity
import android.os.Bundle

class DelegateActivity : Activity() {

    private var token by sharedPreference("user")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = "hello world"
    }
}