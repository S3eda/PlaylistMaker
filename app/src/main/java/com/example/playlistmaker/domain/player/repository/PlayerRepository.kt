package com.example.playlistmaker.domain.player.repository

interface PlayerRepository {
    fun startPlayer()
    fun pausePlayer()
    fun stopPlayer()
    fun preparePlayer(uri: String, isPrepare:()->Unit, isFinish:()->Unit)
    fun playerStatus():Int
    fun getRemainingTime():Int
}

