package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.View
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity(){

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        const val SEARCH_STRING = "SEARCH_STRING"
        const val SOME_TEXT = ""
    }

    var saveSearchText:String = SOME_TEXT
    private val baseUrl = "https://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val searchAPI = retrofit.create(SearchAPI::class.java)
    private var historyAdapter = SongsAdapter(SongsAdapter.searchHistory)
    private var songsList = mutableListOf<SongData>()
    private val songsAdapter = SongsAdapter(songsList)
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

        val historySharedPrefs = getSharedPreferences(App.HISTORY_LIST, MODE_PRIVATE)
        val searchHistoryEx = SearchHistory(historySharedPrefs)
        historySharedPrefs.registerOnSharedPreferenceChangeListener { _, _ ->
            historyAdapter.notifyDataSetChanged()
        }
        searchHistoryEx.completionOfSongAdapterSearchHistory(searchHistoryEx)

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

        searchList.adapter = songsAdapter
        historyList.adapter = historyAdapter

        isHistoryVisible(searchHistoryEx.readHistoryList().toMutableList().isNotEmpty())

        backImage.setOnClickListener {
            finish()
        }

        clearHistoryButton.setOnClickListener {
            searchHistoryEx.clearHistory()
            isHistoryVisible(false)
        }

        if (savedInstanceState != null) {
            saveSearchText = savedInstanceState.getString(SEARCH_STRING, SOME_TEXT)
            searchText.setText(saveSearchText)
        }

        clearButton.setOnClickListener {
            searchText.setText("")
            songsList.clear()
            songsAdapter.notifyDataSetChanged()
            somethingWrongVisibility()
        }

        refreshButton.setOnClickListener {
            somethingWrongVisibility()
            searchTrack()
        }

        searchList.setOnClickListener() {
            val songPageIntent = Intent(this, SongPageActivity::class.java)
            startActivity(songPageIntent)
        }

        searchText.addTextChangedListener(
            beforeTextChanged = {s: CharSequence?, p1: Int, p2: Int, p3: Int ->
                val listForHistoryAdapter = searchHistoryEx
                    .listForAdapter(
                        searchHistoryEx
                            .readHistoryList()
                            .toMutableList(),
                        SongsAdapter.searchHistory
                    )
                val historyAdapter = SongsAdapter(listForHistoryAdapter)
                historyAdapter.notifyDataSetChanged()
            },

            afterTextChanged = { s: Editable? ->
                if (s.toString().isEmpty()) {
                    searchList.isVisible = false
                    isHistoryVisible(s?.isEmpty() == true && historyVisibility(searchHistoryEx))
                    somethingWrongVisibility()
                }
            },

            onTextChanged = { s: CharSequence?, p1: Int, p2: Int, p3: Int ->
                searchList.isVisible = false
                progressBar.isVisible = false
                searchDebounce()
                if (s.toString().isEmpty()){
                    handler.removeCallbacks(searchRunnable)
                }
                clearButton.isVisible = !clearSearchButtonVisibility(s)
                saveSearchText = s.toString()
                isHistoryVisible(s?.isEmpty() == true && historyVisibility(searchHistoryEx))
            }
        )

        searchText.setOnFocusChangeListener{ _, hasFocus ->
            if(hasFocus) {
                searchList.isVisible = true
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_STRING, saveSearchText)
    }

    private fun clearSearchButtonVisibility(s: CharSequence?): Boolean {
        return s.isNullOrEmpty()
    }

        private fun searchTrack (){
            progressBar.isVisible = true
            somethingWrongVisibility()
        searchAPI
            .search(searchText.text.toString())
            .enqueue(object: Callback<SongsResponse> {
                override fun onResponse(
                    call: Call<SongsResponse>,
                    response: Response<SongsResponse>
                ) {
                    if (response.code() == 200) {
                        songsList.clear()
                        if (response.body()?.results?.isNotEmpty() == true) {
                            songsList.addAll(response.body()?.results!!)
                            progressBar.isVisible = false
                            songsAdapter.notifyDataSetChanged()
                        }
                        if (songsList.isEmpty()){
                            showMessage(getString(R.string.empty_search_result), "", false)
                        }
                    } else {
                        showMessage(getString(R.string.trouble_with_internet), "", true)
                    }
                }

                override fun onFailure(call: Call<SongsResponse>, t: Throwable) {
                    showMessage(getString(R.string.trouble_with_internet), "", true)
                }
            })
    }

    private fun showMessage(text: String, additionalMessage: String, internetError: Boolean) {
        progressBar.isVisible = false
        if (internetError) {
            somethingWrongText.visibility = View.VISIBLE
            somethingWrongImage.visibility = View.VISIBLE
            refreshButton.visibility = View.VISIBLE
            songsList.clear()
            songsAdapter.notifyDataSetChanged()
            somethingWrongText.text = text
            somethingWrongImage.setImageResource(R.drawable.trouble_with_internet)
        } else {
            if (text.isNotEmpty()) {
                somethingWrongText.visibility = View.VISIBLE
                somethingWrongImage.visibility = View.VISIBLE
                songsList.clear()
                songsAdapter.notifyDataSetChanged()
                somethingWrongText.text = text
                somethingWrongImage.setImageResource(R.drawable.empty_search)
                if (additionalMessage.isNotEmpty()) {
                    Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun somethingWrongVisibility (){
        somethingWrongText.visibility = View.GONE
        somethingWrongImage.visibility = View.GONE
        refreshButton.visibility = View.GONE
    }

    private fun historyVisibility (ex: SearchHistory):Boolean{
        return ex.readHistoryList()
            .isNotEmpty()
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
}