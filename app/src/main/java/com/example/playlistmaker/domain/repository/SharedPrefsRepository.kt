package com.example.playlistmaker.domain.repository

import com.example.playlistmaker.domain.models.SongData

interface SharedPrefsRepository {
    fun readSongHistory():Array<SongData>
    fun writeSongHistory(data: Array<SongData>)
    fun clearSongHistory()
    fun readSettings(): Boolean
    fun writeSettings(darkTheme: Boolean)
}