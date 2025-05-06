package com.example.playlistmaker.ui.search.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.models.ErrorType
import com.example.playlistmaker.ui.search.model.SearchScreenState
import com.example.playlistmaker.ui.search.presetation.SongsAdapter
import com.example.playlistmaker.ui.search.view_model.SearchViewModel
import com.example.playlistmaker.ui.song_page.activity.SongPageActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    companion object {
        const val SEARCH_STRING = "SEARCH_STRING"
        const val SOME_TEXT = ""
    }

    var saveSearchText: String = SOME_TEXT
    private var songsList = mutableListOf<SongData>()

    private lateinit var searchBinding: ActivitySearchBinding
    private lateinit var historyAdapter: SongsAdapter
    private lateinit var searchAdapter: SongsAdapter
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(searchBinding.root)

        viewModel.onCreate()

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

        searchBinding.searchText.addTextChangedListener(
            beforeTextChanged = { s: CharSequence?, p1: Int, p2: Int, p3: Int ->
            },

            afterTextChanged = { s: Editable? ->
                viewModel.editTextChange(s.toString())
            },

            onTextChanged = { s: CharSequence?, p1: Int, p2: Int, p3: Int ->
                showDefaultState()
                viewModel.getEditTextValue(s.toString())
                searchBinding.clearButton.isVisible = !s.isNullOrEmpty()
            }
        )

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
        searchBinding.clearButton.isVisible = false
    }

    private fun showError(error: ErrorType) {
        searchBinding.progress.isVisible = false
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
    private fun showLoadingState() {
        searchBinding.progress.isVisible = true
    }

    private fun showSearchContent(list: List<SongData>) {
        searchAdapter = SongsAdapter(
            onClickAction = {
                openTrack()
                viewModel.addTrackToHistory(it)
            })
        searchBinding.searchRecyclerView.adapter = searchAdapter
        searchAdapter.setItem(list)
        searchBinding.searchRecyclerView.isVisible = true
    }

    private fun showHistoryContent(list: List<SongData>) {
        historyAdapter = SongsAdapter(
            onClickAction = {
                openTrack()
                viewModel.addTrackToHistory(it)
            })
        searchBinding.historyRecyclerView.adapter = historyAdapter
        historyAdapter.setItem(list)
        searchBinding.historyRecyclerView.isVisible = true
        searchBinding.clearHistoryButton.isVisible = true
        searchBinding.historyTitle.isVisible = true
    }


        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            outState.putString(SEARCH_STRING, saveSearchText)
        }

        private fun openTrack() {
            val songPageIntent =
                Intent(this, SongPageActivity::class.java)
            startActivity(songPageIntent)
        }
    }

