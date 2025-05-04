package com.example.playlistmaker.ui.song_page.view_model

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.player.interactor.PlayerInteractor
import com.example.playlistmaker.ui.song_page.models.PlayerScreenState
import java.text.SimpleDateFormat
import java.util.Locale

class SongPageViewModel(
    private val playerInteractor: PlayerInteractor
): ViewModel() {

    companion object {
        fun getSongPageViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SongPageViewModel(
                    Creator.providePlayerInteractor()
                )
            }
        }

        private val TIMER_TOKEN = Any()
        private const val PLAYER_STATE_DEFAULT = 0
        private const val PLAYER_STATE_PREPARED = 1
        private const val PLAYER_STATE_PLAYING = 2
        private const val PLAYER_STATE_PAUSED = 3
        private val PLAYER_STATE_FINISH = 4
    }

    private lateinit var songURI: String
    var playerState = PLAYER_STATE_DEFAULT

    var songName = ""
    var groupName = ""
    var songDuration = ""
    var songAlbumName = ""
    var songYear = ""
    var songGenre = ""
    var songCountry = ""
    var coverArtwork = ""

    private val playerScreenStateLiveData = MutableLiveData<PlayerScreenState>(
        PlayerScreenState.Content
    )
    fun getPlayerScreenStateLiveData(): LiveData<PlayerScreenState> = playerScreenStateLiveData
    private val handler = Handler(Looper.getMainLooper())

    fun trackInfomation(track: SongData) {
        songName = track.trackName
        groupName = track.artistName
        songDuration = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
        songAlbumName = track.collectionName
        songYear = track.releaseDate.substring(0, 4)
        songGenre = track.primaryGenreName
        songCountry = track.country
        coverArtwork = track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
        songURI = track.previewUrl
        playerInteractor.preparePlayer(songURI, {isPrepared()}, {isFinish()} )
    }

    private fun isPrepared(){
        playerState = PLAYER_STATE_PREPARED
        playerScreenStateLiveData.postValue(PlayerScreenState.Prepared)
    }

    private fun isFinish(){
        playerState = PLAYER_STATE_FINISH
        playerScreenStateLiveData.postValue(PlayerScreenState.Finish)
    }

    fun startTimerTask(startTimer: (duration: Long) -> Runnable) {
        handler.postAtTime(
            startTimer(0L),
            TIMER_TOKEN,
            SystemClock.uptimeMillis()
        )
    }

    fun playbackControl() {
        when (playerState) {
            PLAYER_STATE_PLAYING -> {
                playerInteractor.pausePlayer()
                playerState = playerInteractor.playerStatus()
                playerScreenStateLiveData.postValue(PlayerScreenState.Pause)
            }
            PLAYER_STATE_PREPARED, PLAYER_STATE_PAUSED, PLAYER_STATE_FINISH -> {
                playerInteractor.startPlayer()
                playerState = playerInteractor.playerStatus()
                playerScreenStateLiveData.postValue(PlayerScreenState.Play)
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
}