package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.interactor.HistorySharedPrefsInteractor
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.repository.HistorySharedPrefsRepository

class HistorySharedPrefsInteractorImpl(private val repository: HistorySharedPrefsRepository): HistorySharedPrefsInteractor{

    override fun readSongHistory(): Array<SongData> {
        return repository.readSongHistory()
    }

    override fun writeSongHistory(data: Array<SongData>) {
        repository.writeSongHistory(data)
    }

    override fun clearSongHistory() {
        repository.clearSongHistory()
    }
}