package com.example.playlistmaker.ui.song_page.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.data.player.PlayerRepositoryImpl
import com.example.playlistmaker.data.player.PlayerRepositoryImpl.Companion
import com.example.playlistmaker.databinding.ActivitySongPageBinding
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.ui.song_page.models.PlayerScreenState
import com.example.playlistmaker.ui.song_page.view_model.SongPageViewModel
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale

class SongPageActivity : AppCompatActivity(){

    companion object {
        private const val PLAYER_STATE_PLAYING = 2
        private val DELAY = 1000L
        private val PLAYER_STATE_FINISH = 4
    }

    private lateinit var binding: ActivitySongPageBinding
    private lateinit var playerViewModel: SongPageViewModel
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_page)

        binding = ActivitySongPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playerViewModel = ViewModelProvider(
            this,
            SongPageViewModel.getSongPageViewModelFactory()
        )[SongPageViewModel::class.java]

        val songInformation = intent.extras?.get("SONG_INFORMATION").toString()
        val songExemp = Gson().fromJson(songInformation, SongData::class.java)

        playerViewModel.trackInfomation(songExemp)

        playerViewModel.getPlayerScreenStateLiveData().observe(this){screenState ->
            when(screenState){
                is PlayerScreenState.Default -> showDefaultState()
                is PlayerScreenState.Play -> showPlayState()
                is PlayerScreenState.Pause -> showPauseState()
            }
        }

        binding.songPageBack.setOnClickListener{
            finish()
        }

        binding.playButton.setOnClickListener{
            playerViewModel.playbackControl()
        }
    }

    override fun onPause() {
        playerViewModel.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        playerViewModel.onDestroy()
        super.onDestroy()
    }

    private fun playProgress(duration: Long): Runnable{
        return object : Runnable{
            override fun run() {
                var timeLeft = playerViewModel.getRemainingTime()
                val remainingTime = duration + timeLeft
                when (playerViewModel.playerState) {
                    PLAYER_STATE_PLAYING -> {
                        val sec = remainingTime / DELAY
                        binding.playProgress.text = String.format("%02d:%02d", sec / 60, sec % 60)
                        handler.postDelayed(this, DELAY / 3)
                        playerViewModel.playerState = playerViewModel.getPlayerStatus()
                    }
                    PLAYER_STATE_FINISH -> {
                        binding.playButton.setImageResource(R.drawable.play_button)
                        binding.playProgress.text = String.format("%02d:%02d", 0 / 60, 0 % 60)
                        handler.postDelayed(this, DELAY / 3)
                        handler.removeCallbacksAndMessages(null)
                    }
                }
            }
        }
    }

    private fun showPlayState(){
        binding.playButton.isEnabled = true
        binding.playButton.setImageResource(R.drawable.pause)
        playerViewModel.startTimerTask { playProgress(0L) }
    }
    private fun showPauseState(){
        binding.playButton.setImageResource(R.drawable.play_button)
    }
    private fun showDefaultState(){
        binding.playButton.setImageResource(R.drawable.play_button)
        binding.songYear.text = playerViewModel.songYear
        binding.songGenre.text = playerViewModel.songGenre
        binding.songCountry.text = playerViewModel.songCountry
        binding.songAlbumName.text = playerViewModel.songAlbumName
        binding.groupName.text = playerViewModel.groupName
        binding.songName.text = playerViewModel.songName
        binding.songDuration.text = playerViewModel.songDuration
        binding.playProgress.text = playerViewModel.songDuration
        Glide.with(this)
            .load(playerViewModel.coverArtwork)
            .placeholder(R.drawable.internet_error_icon)
            .transform(RoundedCorners(2))
            .into(binding.trackCover)
    }
}