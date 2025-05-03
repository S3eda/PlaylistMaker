package com.example.playlistmaker.ui.song_page

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySongPageBinding
import com.example.playlistmaker.domain.models.SongData
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale

class SongPageActivity : AppCompatActivity(){

    companion object {
        private const val PLAYER_STATE_DEFAULT = 0
        private const val PLAYER_STATE_PREPARED = 1
        private const val PLAYER_STATE_PLAYING = 2
        private const val PLAYER_STATE_PAUSED = 3
        private val DELAY = 1000L
        private val PLAYER_STATE_FINISH = 4
    }

    private lateinit var songURI: String
    private lateinit var binding: ActivitySongPageBinding

    private val playerInteractor = Creator.providePlayerInteractor()
    private var playerState = PLAYER_STATE_DEFAULT
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_page)

        binding = ActivitySongPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val songInformation = intent.extras?.get("SONG_INFORMATION").toString()
        val songExemp = Gson().fromJson(songInformation, SongData::class.java)
        songURI = songExemp.previewUrl

        playerInteractor.preparePlayer(songURI)
        if(playerState != 0) {
            binding.playButton.isEnabled = true
            binding.playButton.setImageResource(R.drawable.play_button)
            binding.playProgress.text = String.format("%02d:%02d", 0 / 60, 0 % 60)
        }

        binding.songName.text = songExemp.trackName
        binding.groupName.text = songExemp.artistName
        binding.songDuration.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(songExemp.trackTimeMillis)
        binding.songAlbumName.text = songExemp.collectionName
        binding.songYear.text = songExemp.releaseDate.substring(0,4)
        binding.songGenre.text = songExemp.primaryGenreName
        binding.songCountry.text = songExemp.country
        binding.playProgress.text = binding.songDuration.text
        fun getCoverArtwork() = songExemp.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")
        Glide.with(this)
            .load(getCoverArtwork())
            .placeholder(R.drawable.internet_error_icon)
            .transform(RoundedCorners(2))
            .into(binding.trackCover)

        binding.songPageBack.setOnClickListener{
            finish()
        }

        binding.playButton.setOnClickListener{
            playbackControl()
            handler.post {
                playProgress(0L).run()
            }
        }
    }

    override fun onPause() {
        playerInteractor.pausePlayer()
        binding.playButton.setImageResource(R.drawable.play_button)
        super.onPause()
    }

    override fun onDestroy() {
        playerInteractor.stopPlayer()
        super.onDestroy()
    }

    private fun playbackControl() {
        playerState =  playerInteractor.playerStatus()
        when(playerState) {
            PLAYER_STATE_PLAYING -> {
                playerInteractor.pausePlayer()
                playerState =  playerInteractor.playerStatus()
                binding.playButton.setImageResource(R.drawable.play_button)
            }
            PLAYER_STATE_PREPARED, PLAYER_STATE_PAUSED, PLAYER_STATE_FINISH -> {
                playerInteractor.startPlayer()
                playerState =  playerInteractor.playerStatus()
                binding.playButton.setImageResource(R.drawable.pause)
            }
        }
    }

    private fun playProgress(duration: Long): Runnable{
        return object : Runnable{
            override fun run() {
                var timeLeft = playerInteractor.getRemainingTime()
                val remainingTime = duration + timeLeft
                when (playerState) {
                    PLAYER_STATE_PLAYING -> {
                        val sec = remainingTime / DELAY
                        binding.playProgress.text = String.format("%02d:%02d", sec / 60, sec % 60)
                        handler.postDelayed(this, DELAY / 3)
                        playerState = playerInteractor.playerStatus()
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
}