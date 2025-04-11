package com.example.playlistmaker.data.dto

import com.example.playlistmaker.domain.models.SongData

class SongsResponse (
    val searchType: String,
    val expression: String,
    val results: List<SongData>
)