package com.example.playlistmaker.domain.search.history.repository

import com.example.playlistmaker.domain.models.SongData

interface HistorySharedPrefsRepository {
    fun readSongHistory():Array<SongData>
    fun clearSongHistory()
    fun addSongToList (track: SongData)
}