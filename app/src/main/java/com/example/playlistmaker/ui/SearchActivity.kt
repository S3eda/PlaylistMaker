package com.example.playlistmaker.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.ui.presentation.SongsAdapter
import com.example.playlistmaker.domain.Consumer.Consumer
import com.example.playlistmaker.domain.Consumer.ConsumerData
import com.google.gson.Gson

class SearchActivity : AppCompatActivity(){

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        const val SEARCH_STRING = "SEARCH_STRING"
        const val SOME_TEXT = ""
    }

    private val searchHistoryInteractor = Creator.provideHistorySharedPrefsInteractor()
    private val searchSongUseCase = Creator.provideSearchUseCase()

    var saveSearchText:String = SOME_TEXT
    private var songsList = mutableListOf<SongData>()

    val searchAdapter = SongsAdapter(
        onClickAction = {
            openTrack(it)
            val tracks = searchHistoryInteractor.listRefactoring(it)
            updateHistoryAdapter(tracks)
        })
    
    val historyAdapter = SongsAdapter(
        onClickAction = {
            openTrack(it)
            val tracks = searchHistoryInteractor.listRefactoring(it).toList()
            updateHistoryAdapter(tracks)
        })

    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { searchTrack()}

    private lateinit var searchText: EditText
    private lateinit var clearButton: Button
    private lateinit var somethingWrongText: TextView
    private lateinit var somethingWrongImage: ImageView
    private lateinit var refreshButton: Button
    private lateinit var clearHistoryButton: Button
    private lateinit var searchList: RecyclerView
    private lateinit var historyList: RecyclerView
    private lateinit var backImage: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var historyTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val songHistoryList = searchHistoryInteractor.getHistoryList()

        searchHistoryInteractor.fillingListForHistoryAdapter(
            songHistoryList,
            searchHistoryInteractor.readSongHistory().toMutableList()
        )

        updateHistoryAdapter(songHistoryList)

        searchText = findViewById(R.id.searchText)
        clearButton = findViewById(R.id.clearButton)
        somethingWrongText = findViewById(R.id.something_wrong_text)
        somethingWrongImage = findViewById(R.id.something_wrong_image)
        refreshButton = findViewById(R.id.refresh_button)
        clearHistoryButton = findViewById(R.id.clear_history_button)
        searchList = findViewById(R.id.search_recyclerView)
        historyList = findViewById(R.id.history_recyclerView)
        backImage = findViewById(R.id.back_search)
        progressBar = findViewById(R.id.progress)
        historyTitle = findViewById(R.id.history_title)

        searchList.adapter = searchAdapter
        historyList.adapter = historyAdapter

        isHistoryVisible(searchHistoryInteractor.readSongHistory().toMutableList().isNotEmpty())

        backImage.setOnClickListener {
            finish()
        }

        clearHistoryButton.setOnClickListener {
            searchHistoryInteractor.clearSongHistory()
            isHistoryVisible(false)
        }

        if (savedInstanceState != null) {
            saveSearchText = savedInstanceState.getString(SEARCH_STRING, SOME_TEXT)
            searchText.setText(saveSearchText)
        }

        clearButton.setOnClickListener {
            searchText.setText("")
            songsList.clear()
            searchAdapter.notifyDataSetChanged()
            historyAdapter.notifyDataSetChanged()
            somethingWrongVisibility(false)
        }

        refreshButton.setOnClickListener {
            somethingWrongVisibility(false)
            searchTrack()
        }

        searchList.setOnClickListener() {
            val songPageIntent = Intent(this, SongPageActivity::class.java)
            startActivity(songPageIntent)
        }

        searchText.addTextChangedListener(
            beforeTextChanged = {s: CharSequence?, p1: Int, p2: Int, p3: Int ->
            },

            afterTextChanged = { s: Editable? ->
                if (s.toString().isEmpty()) {
                    searchList.isVisible = false
                    isHistoryVisible(s?.isEmpty() == true && searchHistoryInteractor.readSongHistory().isNotEmpty())
                }
                historyAdapter.notifyDataSetChanged()
            },

            onTextChanged = { s: CharSequence?, p1: Int, p2: Int, p3: Int ->
                searchList.isVisible = false
                progressBar.isVisible = false
                somethingWrongVisibility(false)
                if(!s.isNullOrEmpty()) {
                    searchDebounce()
                }
                clearButton.isVisible = !clearSearchButtonVisibility(s)
                saveSearchText = s.toString()
                isHistoryVisible(s?.isEmpty() == true && searchHistoryInteractor.readSongHistory().isNotEmpty())
                historyAdapter.notifyDataSetChanged()
            }
        )

        searchText.setOnFocusChangeListener{ _, hasFocus ->
            if(hasFocus) {
                searchList.isVisible = true
            }
        }
    }

    fun searchTrack() {
        if (searchText.text.isNotEmpty()) {
            isHistoryVisible(false)
            progressBar.isVisible = true
            somethingWrongVisibility(false)
            songsList.clear()
            searchSongUseCase.execute(
                searchText.text.toString(),
                consumer = object : Consumer<List<SongData>> {
                    override fun consume(data: ConsumerData<List<SongData>>) {
                        handler.post {
                            when (data) {
                                is ConsumerData.Error -> showMessage(
                                    getString(R.string.trouble_with_internet),
                                    data.message,
                                    true
                                )

                                is ConsumerData.Data -> {
                                    if (data.value.toMutableList().isEmpty()) {
                                        showMessage(
                                            getString(R.string.empty_search_result),
                                            "",
                                            false
                                        )
                                    } else {
                                        songsList.addAll(data.value.toMutableList())
                                        progressBar.isVisible = false
                                        updateSearchAdapter(songsList)
                                        searchAdapter.notifyDataSetChanged()
                                        searchList.isVisible = true
                                    }
                                }
                            }
                        }
                    }
                })
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_STRING, saveSearchText)
    }

    private fun clearSearchButtonVisibility(s: CharSequence?): Boolean {
        return s.isNullOrEmpty()
    }

    private fun showMessage(text: String, additionalMessage: String, internetError: Boolean) {
        progressBar.isVisible = false
        if (internetError) {
            songsList.clear()
            searchAdapter.notifyDataSetChanged()
            somethingWrongText.text = text
            somethingWrongImage.setImageResource(R.drawable.trouble_with_internet)
            somethingWrongVisibility(true)
        } else {
            if (text.isNotEmpty()) {
                songsList.clear()
                searchAdapter.notifyDataSetChanged()
                somethingWrongText.text = text
                somethingWrongImage.setImageResource(R.drawable.empty_search)
                somethingWrongText.isVisible = true
                somethingWrongImage.isVisible = true
                if (additionalMessage.isNotEmpty()) {
                    Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun somethingWrongVisibility (i: Boolean){
        somethingWrongText.isVisible = i
        somethingWrongImage.isVisible = i
        refreshButton.isVisible = i
    }

    private fun searchDebounce() {
        songsList.clear()
        searchList.isVisible = true
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun isHistoryVisible (i: Boolean){
        historyTitle.isVisible = i
        historyList.isVisible = i
        clearHistoryButton.isVisible = i
    }

    private fun openTrack(track: SongData){
        val songPageIntent =
            Intent(this, SongPageActivity::class.java)
        val json = Gson().toJson(track)
        songPageIntent.putExtra("SONG_INFORMATION", json)
        startActivity(songPageIntent)
    }

    private fun updateHistoryAdapter(item: List<SongData>){
        historyAdapter.setItem(item)
    }

    private fun updateSearchAdapter(item: List<SongData>){
        searchAdapter.setItem(item)
    }
}