package com.example.playlistmaker.domain.impl

import android.widget.ImageButton
import android.widget.TextView
import com.example.playlistmaker.domain.interactor.PlayerInteractor
import com.example.playlistmaker.domain.repository.PlayerRepository

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

    override fun preparePlayer(uri: String) {
        repository.preparePlayer(uri)
    }

    override fun playerStatus(): Int {
        return repository.playerStatus()
    }

    override fun getRemainingTime(): Int {
        return repository.getRemainingTime()
    }
}