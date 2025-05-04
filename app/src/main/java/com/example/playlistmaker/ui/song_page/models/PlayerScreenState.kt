package com.example.playlistmaker.ui.song_page.models

sealed class PlayerScreenState {
    data object Default : PlayerScreenState()
    data object Play : PlayerScreenState()
    data object Pause : PlayerScreenState()
}