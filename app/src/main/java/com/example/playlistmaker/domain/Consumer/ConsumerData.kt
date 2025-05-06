package com.example.playlistmaker.domain.Consumer

import com.example.playlistmaker.domain.models.ErrorType

sealed interface ConsumerData<T> {
    data class Data<T>(val value: T) : ConsumerData<T>
    data class Error<T>(val errorType: ErrorType) : ConsumerData<T>
}