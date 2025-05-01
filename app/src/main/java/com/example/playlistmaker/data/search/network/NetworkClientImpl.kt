package com.example.playlistmaker.data.search.network

import com.example.playlistmaker.data.search.models.NetworkResponse

class NetworkClientImpl(): NetworkClient {

    override fun searchSong(expression: String): NetworkResponse {
        return try {
            val response = RetrofitClient.api.search(expression).execute()
            val networkResponse = response.body() ?: NetworkResponse()
            networkResponse.apply { resultCode = response.code() }
        } catch (ex: Exception) {
            NetworkResponse().apply{ resultCode = -1}
        }
    }
}