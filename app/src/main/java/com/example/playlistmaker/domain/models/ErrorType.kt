package com.example.playlistmaker.domain.models

sealed class ErrorType {
    data object NoInternetConnection : ErrorType()
    data object EmptyResponse : ErrorType()
    data object InvalidRequest : ErrorType()
    data object ServerError : ErrorType()
}