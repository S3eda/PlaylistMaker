package com.example.playlistmaker.data.network

import com.example.playlistmaker.domain.models.SongData

class SongsResponse (
    val searchType: String,
    val expression: String,
    val results: List<SongData>
)
