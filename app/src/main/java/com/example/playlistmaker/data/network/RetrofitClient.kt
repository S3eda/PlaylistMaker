package com.example.playlistmaker.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val baseUrl = "https://itunes.apple.com"

    private val client: Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SearchAPI by lazy{
        client.create(SearchAPI::class.java)
    }
}