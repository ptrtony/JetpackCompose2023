package com.zjq.jetpackcompose2023.delegate

import android.content.Context
import androidx.activity.ComponentActivity
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPreferenceDelegate(
    private val context: Context,
    private val name: String,
    private val valueDefault: String = ""
) : ReadWriteProperty<Any?, String>{

    private val sharedPreference by lazy {
        context.getSharedPreferences("my_prefs", ComponentActivity.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return sharedPreference.getString(name, valueDefault) ?: valueDefault
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        sharedPreference.edit().putString(name, value).apply()
    }
}

fun Context.sharedPreference(name: String) = SharedPreferenceDelegate(this, name)