package com.example.playlistmaker.domain.player.interactor

interface PlayerInteractor {
    fun startPlayer()
    fun pausePlayer()
    fun stopPlayer()
    fun preparePlayer(uri: String)
    fun playerStatus():Int
    fun getRemainingTime():Int
}