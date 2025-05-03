package com.example.playlistmaker.ui.search.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.Consumer.Consumer
import com.example.playlistmaker.domain.Consumer.ConsumerData
import com.example.playlistmaker.domain.models.ErrorType
import com.example.playlistmaker.ui.search.model.SearchScreenState
import com.example.playlistmaker.ui.search.presetation.SongsAdapter
import com.example.playlistmaker.ui.search.view_model.SearchViewModel
import com.example.playlistmaker.ui.song_page.SongPageActivity
import com.google.gson.Gson

class SearchActivity : AppCompatActivity() {

    companion object {
        const val SEARCH_STRING = "SEARCH_STRING"
        const val SOME_TEXT = ""
    }

    private val searchHistoryInteractor = Creator.provideHistorySharedPrefsInteractor()


    var saveSearchText: String = SOME_TEXT
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

    private lateinit var viewModel: SearchViewModel
    private lateinit var searchBinding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProvider(
            this,
            SearchViewModel.getSearchViewModelFactory()
        )[SearchViewModel::class.java]
        searchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(searchBinding.root)
        searchBinding.searchRecyclerView.adapter = searchAdapter
        searchBinding.historyRecyclerView.adapter = historyAdapter

        val songHistoryList = searchHistoryInteractor.getHistoryList()

        searchHistoryInteractor.fillingListForHistoryAdapter(
            songHistoryList,
            searchHistoryInteractor.readSongHistory().toMutableList()
        )

        viewModel.onStartScreenState(songHistoryList)

        searchBinding.backSearch.setOnClickListener {
            finish()
        }

        searchBinding.clearHistoryButton.setOnClickListener {
            viewModel.clearHistory()
        }

        if (savedInstanceState != null) {
            saveSearchText = savedInstanceState.getString(SEARCH_STRING, SOME_TEXT)
            searchBinding.searchText.setText(saveSearchText)
        }

        searchBinding.clearButton.setOnClickListener {
            searchBinding.searchText.setText("")
            songsList.clear()
        }

        searchBinding.refreshButton.setOnClickListener {
            showDefaultState()
            viewModel.searchDebounce(searchBinding.searchText.toString())
        }

        searchBinding.searchRecyclerView.setOnClickListener() {
            val songPageIntent = Intent(this, SongPageActivity::class.java)
            startActivity(songPageIntent)
        }

        searchBinding.searchText.addTextChangedListener(
            beforeTextChanged = { s: CharSequence?, p1: Int, p2: Int, p3: Int ->
            },

            afterTextChanged = { s: Editable? ->
                viewModel.editTextChange(s.toString())
            },

            onTextChanged = { s: CharSequence?, p1: Int, p2: Int, p3: Int ->
                showDefaultState()
                searchBinding.clearButton.isVisible = s.isNullOrEmpty()
                viewModel.getEditTextValue(s.toString())
            }
        )

        searchBinding.searchText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                searchBinding.searchRecyclerView.isVisible = true
            }
        }

        viewModel.getScreenStateLiveData().observe(this) { screenState ->
            when (screenState) {
                is SearchScreenState.Default -> {
                    showDefaultState()
                }

                is SearchScreenState.Loading -> {
                    showLoadingState()
                }

                is SearchScreenState.SearchContent -> {
                    showSearchContent(screenState.list)
                }

                is SearchScreenState.Error -> {
                    showError(screenState.errorType)
                }

                is SearchScreenState.HistoryContent -> {
                    showHistoryContent(screenState.list)
                }
            }
        }
    }

    private fun showDefaultState() {
        searchBinding.progress.isVisible = false
        searchBinding.historyRecyclerView.isVisible = false
        searchBinding.searchRecyclerView.isVisible = false
        searchBinding.somethingWrongText.isVisible = false
        searchBinding.somethingWrongImage.isVisible = false
        searchBinding.refreshButton.isVisible = false
        searchBinding.clearHistoryButton.isVisible = false
        searchBinding.historyTitle.isVisible = false
    }

    private fun showError(error: ErrorType) {
        searchBinding.somethingWrongText.isVisible = true
        searchBinding.somethingWrongImage.isVisible = true
        when (error) {
            ErrorType.NoInternetConnection -> {
                searchBinding.refreshButton.isVisible = true
                searchBinding.somethingWrongImage.setImageResource(R.drawable.trouble_with_internet)
                searchBinding.somethingWrongText.text = getString(R.string.trouble_with_internet)
            }

            ErrorType.EmptyResponse -> {
                searchBinding.refreshButton.isVisible = false
                searchBinding.somethingWrongImage.setImageResource(R.drawable.empty_search)
                searchBinding.somethingWrongText.text = getString(R.string.empty_search_result)
            }

            ErrorType.InvalidRequest -> {
                searchBinding.refreshButton.isVisible = true
                searchBinding.somethingWrongImage.setImageResource(R.drawable.trouble_with_internet)
                searchBinding.somethingWrongText.text = "InvalidRequest"
            }

            ErrorType.ServerError -> {
                searchBinding.refreshButton.isVisible = true
                searchBinding.somethingWrongImage.setImageResource(R.drawable.trouble_with_internet)
                searchBinding.somethingWrongText.text = "ServerError"
            }
        }

    }

    private fun showSearchContent(list: List<SongData>) {
        updateSearchAdapter(list)
        searchBinding.searchRecyclerView.isVisible = true
    }

    private fun showLoadingState() {
        searchBinding.progress.isVisible = true
    }

    private fun showHistoryContent(list: List<SongData>) {
        updateHistoryAdapter(list)
        searchBinding.historyRecyclerView.isVisible = true
        searchBinding.clearHistoryButton.isVisible = true
        searchBinding.historyTitle.isVisible = true
    }


        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            outState.putString(SEARCH_STRING, saveSearchText)
        }

        private fun openTrack(track: SongData) {
            val songPageIntent =
                Intent(this, SongPageActivity::class.java)
            val json = Gson().toJson(track)
            songPageIntent.putExtra("SONG_INFORMATION", json)
            startActivity(songPageIntent)
        }

        private fun updateHistoryAdapter(item: List<SongData>) {
            historyAdapter.setItem(item)
        }

        private fun updateSearchAdapter(item: List<SongData>) {
            searchAdapter.setItem(item)
        }
    }

