package com.example.playlistmaker.domain.models

import androidx.core.app.NotificationCompat.MessagingStyle.Message

sealed interface Resourse<T> {
    data class Success<T>(val data: T) : Resourse<T>
    data class Error<T>(val message: String) : Resourse<T>
}