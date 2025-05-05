package com.example.playlistmaker.domain.search.history.interactor

import com.example.playlistmaker.domain.models.SongData

interface HistorySharedPrefsInteractor {
    fun readSongHistory():Array<SongData>
    fun clearSongHistory()
    fun addSongToList (track: SongData)
}