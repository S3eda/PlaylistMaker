package com.example.playlistmaker.data.models

data class SongDataResponse (
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: Long,
    val trackId: Int,
    val collectionName: String,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String
)