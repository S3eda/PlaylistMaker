package com.example.playlistmaker.data.impl

import android.media.MediaPlayer
import android.widget.ImageButton
import android.widget.TextView
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.repository.PlayerRepository

class PlayerRepositoryImpl(): PlayerRepository {

    private val PLAYER_STATE_DEFAULT = 0
    private val PLAYER_STATE_PREPARED = 1
    private val PLAYER_STATE_PLAYING = 2
    private val PLAYER_STATE_PAUSED = 3

    private var player = PLAYER_STATE_DEFAULT

    private val mediaPlayer = MediaPlayer()

    override fun startPlayer(){
        mediaPlayer.start()
        player = PLAYER_STATE_PLAYING
    }

    override fun pausePlayer() {
        mediaPlayer.pause()
        player = PLAYER_STATE_PAUSED
    }

    override fun stopPlayer() {
        mediaPlayer.release()
    }

    override fun preparePlayer(uri: String) {
        mediaPlayer.setDataSource(uri)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener{
            player = PLAYER_STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener{
            player = PLAYER_STATE_PREPARED
        }
    }

    override fun playerStatus(): Int {
        return player
    }
}