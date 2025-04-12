package com.example.playlistmaker.domain.Consumer

interface Consumer<T> {
    fun consume(data: ConsumerData<T>)
}