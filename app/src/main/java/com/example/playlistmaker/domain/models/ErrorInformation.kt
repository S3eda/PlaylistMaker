package com.example.playlistmaker.domain.models

data class ErrorInformation (
    val text: String,
    val additionalMessage: String,
    val internetError: Boolean
)
