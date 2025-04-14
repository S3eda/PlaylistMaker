package com.example.playlistmaker.domain.repository

import com.example.playlistmaker.domain.models.SongData

interface HistorySharedPrefsRepository {
    fun readSongHistory():Array<SongData>
    fun writeSongHistory(data: Array<SongData>)
    fun clearSongHistory()
}