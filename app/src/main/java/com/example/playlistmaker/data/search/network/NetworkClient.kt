package com.example.playlistmaker.data.search.network

import com.example.playlistmaker.data.search.models.NetworkResponse

interface NetworkClient {
    fun searchSong (expression: String): NetworkResponse
}