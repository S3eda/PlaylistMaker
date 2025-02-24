package com.example.playlistmaker

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale

class SongPageActivity : AppCompatActivity(){

    private lateinit var songName:TextView
    private lateinit var groupName:TextView
    private lateinit var songDuration:TextView
    private lateinit var albumName:TextView
    private lateinit var year:TextView
    private lateinit var genre:TextView
    private lateinit var country:TextView
    private lateinit var progress:TextView
    private lateinit var trackCover:ImageView
    private lateinit var backButton:ImageView
    private lateinit var playButton:ImageButton
    private lateinit var likeButton:ImageButton
    private lateinit var playlistButton:ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_page)
        val historySharedPrefs = getSharedPreferences(App.HISTORY_LIST, MODE_PRIVATE)
        val searchHistoryEx = SearchHistory(historySharedPrefs)
        searchHistoryEx.writeHistoryList(SongsAdapter.searchHistory.toTypedArray())

        songName = findViewById(R.id.song_name)
        groupName = findViewById(R.id.group_name)
        songDuration = findViewById(R.id.song_duration)
        albumName = findViewById(R.id.song_album_name)
        year = findViewById(R.id.song_year)
        genre = findViewById(R.id.song_genre)
        country = findViewById(R.id.song_country)
        progress = findViewById(R.id.play_progress)
        trackCover = findViewById(R.id.track_cover)
        backButton = findViewById(R.id.song_page_back)
        playButton = findViewById(R.id.play_button)
        likeButton = findViewById(R.id.like_button)
        playlistButton = findViewById(R.id.playlist_button)

        val songInformation = intent.extras?.get("SONG_INFORMATION").toString()
        val songExemp = Gson().fromJson(songInformation, SongData::class.java)

        songName.text = songExemp.trackName
        groupName.text = songExemp.artistName
        songDuration.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(songExemp.trackTimeMillis)
        albumName.text = songExemp.collectionName
        year.text = songExemp.releaseDate.substring(0,4)
        genre.text = songExemp.primaryGenreName
        country.text = songExemp.country
        progress.text = songDuration.text
        fun getCoverArtwork() = songExemp.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")
        Glide.with(this)
            .load(getCoverArtwork())
            .placeholder(R.drawable.internet_error_icon)
            .transform(RoundedCorners(2))
            .into(trackCover)

        backButton.setOnClickListener{
            finish()
        }
    }
}