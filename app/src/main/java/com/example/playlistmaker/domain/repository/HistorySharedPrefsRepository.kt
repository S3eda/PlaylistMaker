package com.example.playlistmaker.domain.repository

import com.example.playlistmaker.domain.models.SongData

interface HistorySharedPrefsRepository {
    fun readSongHistory():Array<SongData>
    fun writeSongHistory(data: Array<SongData>)
    fun clearSongHistory()
    fun fillingListForHistoryAdapter(list1: MutableList<SongData>, list2: MutableList<SongData>)
    fun listRefactoring (track: SongData):MutableList<SongData>
    fun getHistoryList():MutableList<SongData>
}