package com.example.playlistmaker.domain.search.history.impl

import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.search.history.interactor.HistorySharedPrefsInteractor
import com.example.playlistmaker.domain.search.history.repository.HistorySharedPrefsRepository

class HistorySharedPrefsInteractorImpl(private val repository: HistorySharedPrefsRepository):
    HistorySharedPrefsInteractor {

    override fun readSongHistory(): Array<SongData> {
        return repository.readSongHistory()
    }

    override fun clearSongHistory() {
        repository.clearSongHistory()
    }

    override fun addSongToList(track: SongData){
        repository.addSongToList(track)
    }
}