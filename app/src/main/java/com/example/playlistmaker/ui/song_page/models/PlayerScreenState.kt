package com.example.playlistmaker.ui.song_page.models

sealed class PlayerScreenState {
    data object Play : PlayerScreenState()
    data object Pause : PlayerScreenState()
    data object Content : PlayerScreenState()
    data object Preparing : PlayerScreenState()
    data object Finish : PlayerScreenState()
}