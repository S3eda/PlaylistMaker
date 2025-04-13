package com.example.playlistmaker.domain.interactor

import com.example.playlistmaker.domain.models.SongData

interface SongSearchHistoryInteractor {
    fun readHistory():Array<SongData>
    fun writeHistory(data: Array<SongData>)
    fun clearHistory()
}