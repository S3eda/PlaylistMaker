package com.example.playlistmaker.domain.repository

import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

interface PlayerRepository {
    fun startPlayer()
    fun pausePlayer()
    fun stopPlayer()
    fun preparePlayer(uri: String, button: ImageButton, text: TextView)
    fun playerStatus():Int
    fun startTimer(duration: Long, text: TextView)
    fun createUpdateTimerTask(duration: Long, text: TextView): Runnable
}

