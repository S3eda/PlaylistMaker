package com.example.playlistmaker.data.player

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.example.playlistmaker.Constants.PLAYER_STATE_DEFAULT
import com.example.playlistmaker.Constants.PLAYER_STATE_FINISH
import com.example.playlistmaker.Constants.PLAYER_STATE_PAUSED
import com.example.playlistmaker.Constants.PLAYER_STATE_PLAYING
import com.example.playlistmaker.Constants.PLAYER_STATE_PREPARED
import com.example.playlistmaker.domain.player.repository.PlayerRepository

class PlayerRepositoryImpl(private val mediaPlayer: MediaPlayer): PlayerRepository {

    private var player = PLAYER_STATE_DEFAULT
    private val handler = Handler(Looper.getMainLooper())

    override fun startPlayer(){
        mediaPlayer.start()
        player = PLAYER_STATE_PLAYING
    }

    override fun pausePlayer() {
        if(mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            player = PLAYER_STATE_PAUSED
        }
    }

    override fun stopPlayer() {
        handler.removeCallbacksAndMessages(null)
        mediaPlayer.release()
        player = PLAYER_STATE_DEFAULT
    }

    override fun preparePlayer(uri: String, isPrepare:()->Unit, isFinish:()->Unit) {
        mediaPlayer.setDataSource(uri)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            player = PLAYER_STATE_PREPARED
            isPrepare()
        }
        mediaPlayer.setOnCompletionListener {
            player = PLAYER_STATE_FINISH
            isFinish()
        }
    }

    override fun playerStatus(): Int {
        return player
    }

    override fun getRemainingTime():Int{
        return mediaPlayer.getCurrentPosition()
    }
}