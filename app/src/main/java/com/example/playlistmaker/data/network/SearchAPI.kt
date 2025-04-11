package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.dto.SongsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAPI {
        @GET ("search?entity=song")
        fun search(
            @Query("term", encoded = false) text: String
        ): Call<SongsResponse>
    }

