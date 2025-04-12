package com.example.playlistmaker.data.impl

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.TextView
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.repository.PlayerRepository

private val PLAYER_STATE_DEFAULT = 0
private val PLAYER_STATE_PREPARED = 1
private val PLAYER_STATE_PLAYING = 2
private val PLAYER_STATE_PAUSED = 3
private var player = PLAYER_STATE_DEFAULT
private val handler = Handler(Looper.getMainLooper())
private val DELAY = 1000L
private val mediaPlayer = MediaPlayer()

class PlayerRepositoryImpl(): PlayerRepository {

    override fun startPlayer(){
        mediaPlayer.start()
        player = PLAYER_STATE_PLAYING
    }

    override fun pausePlayer() {
        mediaPlayer.pause()
        player = PLAYER_STATE_PAUSED
    }

    override fun stopPlayer() {
        handler.removeCallbacksAndMessages(null)
        mediaPlayer.release()
    }

    override fun preparePlayer(uri: String, button: ImageButton, text: TextView) {
        mediaPlayer.setDataSource(uri)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener{
            button.isEnabled = true
            player = PLAYER_STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener{
            player = PLAYER_STATE_PREPARED
            button.setImageResource(R.drawable.play_button)
            text.text = String.format("%02d:%02d", 0 / 60, 0 % 60)
        }
    }

    override fun playerStatus(): Int {
        return player
    }

    override fun startTimer(duration: Long, text: TextView) {
        handler.post(
            createUpdateTimerTask(duration, text)
        )
    }

    override fun createUpdateTimerTask(duration: Long, text: TextView): Runnable {
        return object : Runnable {
            override fun run() {
                val timeLeft = mediaPlayer.getCurrentPosition()
                val remainingTime = duration + timeLeft

                if (player == 2 && remainingTime < 30000L) {
                    val seconds = remainingTime / DELAY
                    text.text = String.format("%02d:%02d", seconds / 60, seconds % 60)
                    handler.postDelayed(this, DELAY / 3)
                }
            }
        }
    }
}