package com.example.playlistmaker.ui.song_page.models

sealed class PlayerScreenState {
    data class Play(val timerValue: String) : PlayerScreenState()
    data object Pause : PlayerScreenState()
    data class Content(val track: Track) : PlayerScreenState()
}