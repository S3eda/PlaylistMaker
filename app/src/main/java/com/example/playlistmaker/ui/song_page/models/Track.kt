package com.example.playlistmaker.ui.song_page.models

data class Track (
    val songName: String,
    val artistName: String,
    val songDuration: String,
    val songAlbumName: String,
    val songYear: String,
    val songGenre: String,
    val songCountry: String,
    val coverArtwork: String,
    val songURI: String
    )