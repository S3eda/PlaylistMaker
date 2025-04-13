package com.example.playlistmaker.domain.interactor

import com.example.playlistmaker.domain.models.SongData

interface SharedPrefsInteractor {
    fun readSongHistory():Array<SongData>
    fun writeSongHistory(data: Array<SongData>)
    fun clearSongHistory()
    fun readSettings(): Boolean
    fun writeSettings(darkTheme: Boolean)
}