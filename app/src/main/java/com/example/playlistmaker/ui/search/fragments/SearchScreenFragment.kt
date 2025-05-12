package com.example.playlistmaker.ui.search.fragments

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.binding.BindingFragment
import com.example.playlistmaker.databinding.SearchFragmentBinding
import com.example.playlistmaker.domain.models.ErrorType
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.ui.search.model.SearchScreenState
import com.example.playlistmaker.ui.search.presetation.SongsAdapter
import com.example.playlistmaker.ui.search.view_model.SearchFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchScreenFragment : BindingFragment<SearchFragmentBinding>() {

    companion object {
        fun newInstance(): SearchScreenFragment = SearchScreenFragment()

        const val SEARCH_STRING = "SEARCH_STRING"
        const val SOME_TEXT = ""
    }

    private lateinit var searchAdapter: SongsAdapter
    private lateinit var historyAdapter: SongsAdapter

    var saveSearchText: String = SOME_TEXT
    private var songsList = mutableListOf<SongData>()

    private val viewModel: SearchFragmentViewModel by viewModel()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): SearchFragmentBinding {
        return SearchFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyAdapter = SongsAdapter(
            onClickAction = {
                openTrack()
                viewModel.addTrackToHistory(it)
            })
        searchAdapter = SongsAdapter(
            onClickAction = {
                openTrack()
                viewModel.addTrackToHistory(it)
            })

        viewModel.onCreate()

        binding.clearHistoryButton.setOnClickListener {
            viewModel.clearHistory()
        }

        if (savedInstanceState != null) {
            saveSearchText = savedInstanceState.getString(SEARCH_STRING, SOME_TEXT)
            binding.searchText.setText(saveSearchText)
        }

        binding.clearButton.setOnClickListener {
            binding.searchText.setText("")
            songsList.clear()
        }

        binding.refreshButton.setOnClickListener {
            showDefaultState()
            viewModel.searchDebounce(binding.searchText.toString())
        }

        binding.searchText.addTextChangedListener(

            afterTextChanged = { s: Editable? ->
                viewModel.editTextChange(s.toString())
            },

            onTextChanged = { s: CharSequence?, _: Int, _: Int, _: Int ->
                showDefaultState()
                viewModel.getEditTextValue(s.toString())
                binding.clearButton.isVisible = !s.isNullOrEmpty()
            }
        )

        viewModel.getScreenStateLiveData().observe(viewLifecycleOwner) { screenState ->
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
        binding.progress.isVisible = false
        binding.historyRecyclerView.isVisible = false
        binding.searchRecyclerView.isVisible = false
        binding.somethingWrongText.isVisible = false
        binding.somethingWrongImage.isVisible = false
        binding.refreshButton.isVisible = false
        binding.clearHistoryButton.isVisible = false
        binding.historyTitle.isVisible = false
        binding.clearButton.isVisible = false
    }

    private fun showError(error: ErrorType) {
        binding.progress.isVisible = false
        binding.somethingWrongText.isVisible = true
        binding.somethingWrongImage.isVisible = true
        when (error) {
            ErrorType.NoInternetConnection -> {
                binding.refreshButton.isVisible = true
                binding.somethingWrongImage.setImageResource(R.drawable.trouble_with_internet)
                binding.somethingWrongText.text = getString(R.string.trouble_with_internet)
            }

            ErrorType.EmptyResponse -> {
                binding.refreshButton.isVisible = false
                binding.somethingWrongImage.setImageResource(R.drawable.empty_search)
                binding.somethingWrongText.text = getString(R.string.empty_search_result)
            }

            ErrorType.InvalidRequest -> {
                binding.refreshButton.isVisible = true
                binding.somethingWrongImage.setImageResource(R.drawable.trouble_with_internet)
                binding.somethingWrongText.text = "InvalidRequest"
            }

            ErrorType.ServerError -> {
                binding.refreshButton.isVisible = true
                binding.somethingWrongImage.setImageResource(R.drawable.trouble_with_internet)
                binding.somethingWrongText.text = "ServerError"
            }
        }

    }
    private fun showLoadingState() {
        binding.progress.isVisible = true
    }

    private fun showSearchContent(list: List<SongData>) {
        binding.searchRecyclerView.adapter = searchAdapter
        searchAdapter.setItem(list)
        binding.searchRecyclerView.isVisible = true
    }

    private fun showHistoryContent(list: List<SongData>) {
        binding.historyRecyclerView.adapter = historyAdapter
        historyAdapter.setItem(list)
        binding.historyRecyclerView.isVisible = true
        binding.clearHistoryButton.isVisible = true
        binding.historyTitle.isVisible = true
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_STRING, saveSearchText)
    }

    private fun openTrack() {
        findNavController().navigate(R.id.action_searchScreenFragment_to_songPageActivity2)
    }
}


