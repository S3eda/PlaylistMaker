package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.interactor.SettingsSharedPrefsInteractor
import com.example.playlistmaker.domain.repository.SettingsSharedPrefsRepository

class SettingsSharedPrefsInteractorImpl(
    private val repository: SettingsSharedPrefsRepository
): SettingsSharedPrefsInteractor {

    override fun readSettings(): Boolean {
        return repository.readSettings()
    }

    override fun writeSettings(darkTheme: Boolean) {
        repository.writeSettings(darkTheme)
    }

   override fun switchTheme(darkThemeEnabled: Boolean): Int {
        return repository.switchTheme(darkThemeEnabled)
    }
}