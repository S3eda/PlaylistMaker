package com.example.playlistmaker.domain.impl

import android.widget.Button
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

    override fun preparePlayer(uri: String, button: ImageButton, text: TextView) {
        repository.preparePlayer(uri, button, text)
    }

    override fun playerStatus(): Int {
        return repository.playerStatus()
    }

    override fun startTimer(duration: Long, text: TextView){
        repository.startTimer(duration, text)
    }

    override fun createUpdateTimerTask(duration: Long, text: TextView): Runnable {
        return repository.createUpdateTimerTask(duration, text)
    }
}