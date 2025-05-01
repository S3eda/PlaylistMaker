package com.example.playlistmaker.domain.search.history.interactor

import com.example.playlistmaker.domain.models.SongData

interface HistorySharedPrefsInteractor {
    fun readSongHistory():Array<SongData>
    fun writeSongHistory(data: Array<SongData>)
    fun clearSongHistory()
    fun fillingListForHistoryAdapter(list1: MutableList<SongData>, list2: MutableList<SongData>)
    fun listRefactoring (track: SongData):MutableList<SongData>
    fun getHistoryList():MutableList<SongData>
}