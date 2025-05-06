package com.example.playlistmaker.domain.player.impl

import com.example.playlistmaker.domain.player.interactor.PlayerInteractor
import com.example.playlistmaker.domain.player.repository.PlayerRepository

class PlayerInteractorImpl(private val repository: PlayerRepository): PlayerInteractor {
    override fun startPlayer() {
        repository.startPlayer()
    }

    override fun pausePlayer() {
        repository.pausePlayer()
    }

    override fun stopPlayer() {
        repository.stopPlayer()
    }

    override fun preparePlayer(uri: String, isPrepare:()->Unit, isFinish:()->Unit) {
        repository.preparePlayer(uri, isPrepare, isFinish)
    }

    override fun playerStatus(): Int {
        return repository.playerStatus()
    }

    override fun getRemainingTime(): Int {
        return repository.getRemainingTime()
    }
}