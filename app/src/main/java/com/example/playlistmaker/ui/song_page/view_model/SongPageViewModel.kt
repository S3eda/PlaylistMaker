package com.example.playlistmaker.ui.song_page.view_model

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.player.interactor.PlayerInteractor
import com.example.playlistmaker.domain.search.history.interactor.HistorySharedPrefsInteractor
import com.example.playlistmaker.ui.song_page.models.PlayerScreenState
import com.example.playlistmaker.ui.song_page.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class SongPageViewModel(
    private val playerInteractor: PlayerInteractor,
    private val historyInteractor: HistorySharedPrefsInteractor
): ViewModel() {

    companion object {
        private const val PLAYER_STATE_DEFAULT = 0
        private const val PLAYER_STATE_PREPARED = 1
        private const val PLAYER_STATE_PLAYING = 2
        private const val PLAYER_STATE_PAUSED = 3
        private const val PLAYER_STATE_FINISH = 4
        private const val ONE_SEC_DELAY = 1000L
        const val TIMER_ZERO_VALUE = "00:00"
    }

    private val TIMER_TOKEN = Any()
    var playerState = PLAYER_STATE_DEFAULT

    private fun getSong(): SongData {
        var song: SongData? = null
        if (historyInteractor.readSongHistory().size != 0) {
            song = historyInteractor.readSongHistory()[0]
        }
        return song!!
    }

    private var timer: String = TIMER_ZERO_VALUE

    private val playerScreenStateLiveData = MutableLiveData<PlayerScreenState>(
        PlayerScreenState.Content(trackInfomation(getSong()))
    )

    fun getPlayerScreenStateLiveData(): LiveData<PlayerScreenState> = playerScreenStateLiveData
    private val handler = Handler(Looper.getMainLooper())

    fun trackInfomation(track: SongData): Track {
        return Track(
            songName = track.trackName,
            artistName = track.artistName,
            songDuration = SimpleDateFormat(
                "mm:ss",
                Locale.getDefault()
            ).format(track.trackTimeMillis),
            songAlbumName = track.collectionName,
            songYear = track.releaseDate.substring(0, 4),
            songGenre = track.primaryGenreName,
            songCountry = track.country,
            coverArtwork = track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"),
            songURI = track.previewUrl
        )
    }

    fun preparePlayer() {
        playerInteractor.preparePlayer(trackInfomation(getSong()).songURI, { isPrepared() }, { isFinish() })
    }

    private fun isPrepared() {
        playerState = PLAYER_STATE_PREPARED
    }

    private fun isFinish() {
        playerState = PLAYER_STATE_FINISH
        playerScreenStateLiveData.postValue(PlayerScreenState.Content(trackInfomation(getSong())))
        handler.removeCallbacksAndMessages(TIMER_TOKEN)
    }

    fun playbackControl() {
        when (playerState) {
            PLAYER_STATE_PLAYING -> {
                playerInteractor.pausePlayer()
                playerState = playerInteractor.playerStatus()
                playerScreenStateLiveData.postValue(PlayerScreenState.Pause)
            }
            PLAYER_STATE_PREPARED, PLAYER_STATE_PAUSED, PLAYER_STATE_FINISH -> {
                playTrack()
                playerState = playerInteractor.playerStatus()
                playerScreenStateLiveData.postValue(PlayerScreenState.Play(timer))
            }
        }
    }

    fun onPause() {
        playerInteractor.pausePlayer()
        playerScreenStateLiveData.postValue(PlayerScreenState.Pause)
    }

    fun onDestroy() {
        playerInteractor.stopPlayer()
        handler.removeCallbacksAndMessages(TIMER_TOKEN)
    }

    fun getRemainingTime(): Long {
        return playerInteractor.getRemainingTime().toLong()
    }

    fun getPlayerStatus(): Int {
        return playerInteractor.playerStatus()
    }

    private fun playTrack() {
        playerState = PLAYER_STATE_PLAYING
        playerInteractor.startPlayer()
        handler.postAtTime(
            playProgress(0L),
            TIMER_TOKEN,
            SystemClock.uptimeMillis()
        )
    }

    private fun playProgress(duration: Long): Runnable {
        return object : Runnable {
            override fun run() {
                var timeLeft = getRemainingTime()
                val remainingTime = duration + timeLeft
                when (playerState) {
                    PLAYER_STATE_PLAYING -> {
                        val sec = remainingTime / ONE_SEC_DELAY
                        timer = String.format("%02d:%02d", sec / 60, sec % 60)
                        handler.postDelayed(this, ONE_SEC_DELAY / 3)
                        playerScreenStateLiveData.postValue(PlayerScreenState.Play(timer))
                        playerState = getPlayerStatus()
                    }

                    PLAYER_STATE_FINISH -> {
                        handler.postDelayed(this, ONE_SEC_DELAY / 3)
                        handler.removeCallbacksAndMessages(null)
                    }
                }
            }
        }
    }
}