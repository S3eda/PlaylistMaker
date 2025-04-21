package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.models.NetworkResponse

interface NetworkClient {
    fun searchSong (expression: String): NetworkResponse
}