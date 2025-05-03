package com.example.playlistmaker.ui.search.view_model

import android.content.Intent
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
import com.example.playlistmaker.domain.Consumer.Consumer
import com.example.playlistmaker.domain.Consumer.ConsumerData
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.search.history.interactor.HistorySharedPrefsInteractor
import com.example.playlistmaker.domain.search.useCase.SearchSongUseCase
import com.example.playlistmaker.ui.search.model.SearchScreenState
import com.example.playlistmaker.ui.search.presetation.SongsAdapter
import com.example.playlistmaker.ui.song_page.SongPageActivity
import com.google.gson.Gson

class SearchViewModel(
    private val searchHistoryInteractor: HistorySharedPrefsInteractor,
    private val searchUseCase: SearchSongUseCase
) : ViewModel() {

    companion object{
        fun getSearchViewModelFactory():ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(
                    Creator.provideHistorySharedPrefsInteractor(),
                    Creator.provideSearchUseCase()
                )
            }
        }

        private val SEARCH_REQUEST_TOKEN = Any()
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val EMPTY_STRING = ""
    }

    var correctEditTextValue = ""

    private val screenStateLiveData = MutableLiveData<SearchScreenState>(SearchScreenState.Default)
    fun getScreenStateLiveData(): LiveData<SearchScreenState> = screenStateLiveData
    private var songsList = mutableListOf<SongData>()
    private val handler = Handler(Looper.getMainLooper())
    private var lastRequest = EMPTY_STRING

    fun searchTrack(expression: String) {
        if (correctEditTextValue.isNotEmpty()) {
            songsList.clear()
            searchUseCase.execute(
                expression,
                consumer = object : Consumer<List<SongData>> {
                    override fun consume(data: ConsumerData<List<SongData>>) {
                        when (data) {
                            is ConsumerData.Error -> {
                                screenStateLiveData.postValue(SearchScreenState.Error(data.errorType))
                            }

                            is ConsumerData.Data -> {
                                screenStateLiveData.postValue(SearchScreenState.SearchContent(data.value))
                            }
                        }
                    }
                }
            )
        }
    }

    fun searchDebounce(changeText: String) {
        screenStateLiveData.postValue(SearchScreenState.Default)
        screenStateLiveData.postValue(SearchScreenState.Loading)
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        val searchRunnable = Runnable { searchTrack(changeText) }
        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime
        )

    }

    fun clearHistory(){
        screenStateLiveData.postValue(SearchScreenState.Default)
        searchHistoryInteractor.clearSongHistory()
    }

    fun editTextChange (newRequest: String) {
        screenStateLiveData.postValue(SearchScreenState.Default)

        if (newRequest.isEmpty() && searchHistoryInteractor.readSongHistory().isNotEmpty()) {
            handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
            screenStateLiveData.postValue(SearchScreenState.HistoryContent(searchHistoryInteractor.readSongHistory().toMutableList()))
            return
        }

        if (newRequest == lastRequest || newRequest.isEmpty()) return
        lastRequest = newRequest

        searchDebounce(newRequest)
    }

    fun getEditTextValue(string: String){
        correctEditTextValue = string
    }

    fun onStartScreenState(list: List<SongData>) {
        if (list.isEmpty()) {
            screenStateLiveData.postValue(SearchScreenState.Default)
        } else {
            screenStateLiveData.postValue(SearchScreenState.HistoryContent(list))
        }
    }
}