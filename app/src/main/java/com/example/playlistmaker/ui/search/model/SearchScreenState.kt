package com.example.playlistmaker.ui.search.model

import com.example.playlistmaker.domain.models.ErrorType
import com.example.playlistmaker.domain.models.SongData

sealed class SearchScreenState {
    data object Default : SearchScreenState()
    data class HistoryContent(val list: List<SongData>):SearchScreenState()
    data class SearchContent(val list: List<SongData>):SearchScreenState()
    data class Error(val errorType: ErrorType) : SearchScreenState()
    data object Loading : SearchScreenState()
}