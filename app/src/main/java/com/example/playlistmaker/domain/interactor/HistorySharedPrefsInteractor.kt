package com.example.playlistmaker.domain.interactor

import com.example.playlistmaker.domain.models.SongData

interface HistorySharedPrefsInteractor {
    fun readSongHistory():Array<SongData>
    fun writeSongHistory(data: Array<SongData>)
    fun clearSongHistory()
}