package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.interactor.SongSearchHistoryInteractor
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.repository.SongSearchHistoryRepository

class SongSearchHistoryInteractorImpl(private val repository: SongSearchHistoryRepository): SongSearchHistoryInteractor{

    override fun readHistory(): Array<SongData> {
        return repository.readHistory()
    }

    override fun writeHistory(data: Array<SongData>) {
        repository.writeHistory(data)
    }

    override fun clearHistory() {
        repository.clearHistory()
    }
}