package com.example.playlistmaker.ui.song_page.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ComponentActivity
import androidx.core.view.isVisible
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

    private lateinit var binding: ActivitySongPageBinding
    private lateinit var playerViewModel: SongPageViewModel

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
                is PlayerScreenState.Play -> showPlayState()
                is PlayerScreenState.Pause -> showPauseState()
                is PlayerScreenState.Content -> showConten()
                is PlayerScreenState.Prepared -> showPrepared()
                is PlayerScreenState.PlayerPreparing -> showPlayerPreparing()
            }
        }

        playerViewModel.addTimerObserver { timer ->
            changePlayProgress(timer)
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
        playerViewModel.removeTimerObserver()
        super.onDestroy()
    }

    private fun showPlayState(){
        binding.playButton.setImageResource(R.drawable.pause)
    }
    private fun showPauseState(){
        binding.playButton.setImageResource(R.drawable.play_button)
    }
    private fun showConten(){
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
    private fun showPrepared(){
        binding.playButton.isEnabled = true
    }
    private fun showPlayerPreparing(){
        binding.playButton.isEnabled = false
    }

    private fun changePlayProgress(text: String){
        binding.playProgress.text = text
    }
}