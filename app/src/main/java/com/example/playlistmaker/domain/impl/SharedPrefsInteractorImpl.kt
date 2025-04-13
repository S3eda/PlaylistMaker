package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.interactor.SharedPrefsInteractor
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.repository.SharedPrefsRepository

class SharedPrefsInteractorImpl(private val repository: SharedPrefsRepository): SharedPrefsInteractor{

    override fun readSongHistory(): Array<SongData> {
        return repository.readSongHistory()
    }

    override fun writeSongHistory(data: Array<SongData>) {
        repository.writeSongHistory(data)
    }

    override fun clearSongHistory() {
        repository.clearSongHistory()
    }

    override fun readSettings(): Boolean {
        return repository.readSettings()
    }

    override fun writeSettings(darkTheme: Boolean) {
        repository.writeSettings(darkTheme)
    }
}