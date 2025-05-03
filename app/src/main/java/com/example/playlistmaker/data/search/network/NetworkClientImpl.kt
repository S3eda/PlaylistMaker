package com.example.playlistmaker.data.search.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.playlistmaker.data.search.models.NetworkResponse

class NetworkClientImpl(private val context: Context): NetworkClient {

    override fun searchSong(expression: String): NetworkResponse {
        if (isConnected() == false) {
            return NetworkResponse().apply { resultCode = -1 }
        }
        val response = RetrofitClient.api.search(expression).execute()
        val networkResponse = response.body() ?: NetworkResponse()
        return networkResponse.apply { resultCode = response.code() }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}