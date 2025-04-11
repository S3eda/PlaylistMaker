package com.example.playlistmaker.domain.repository

import com.example.playlistmaker.domain.models.SongData

interface SongSearchHistoryRepository {
    fun readHistory():Array<SongData>
    fun writeHistory(data: Array<SongData>)
    fun clearHistory()
}