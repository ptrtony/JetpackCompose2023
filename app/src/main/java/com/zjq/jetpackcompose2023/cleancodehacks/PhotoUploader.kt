package com.zjq.jetpackcompose2023.cleancodehacks

typealias PhotoUrl = String

interface PhotoUploader {
    suspend fun uploadPhoto(bytes: ByteArray) : Result<PhotoUrl>
}