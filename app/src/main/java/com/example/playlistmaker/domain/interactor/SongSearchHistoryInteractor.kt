package com.example.playlistmaker.domain.interactor

import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.presentation.SongsAdapter

interface SongSearchHistoryInteractor {
    fun readHistory():Array<SongData>
    fun writeHistory(data: Array<SongData>)
    fun clearHistory()
}