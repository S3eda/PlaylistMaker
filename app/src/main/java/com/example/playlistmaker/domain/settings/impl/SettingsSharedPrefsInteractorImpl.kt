package com.example.playlistmaker.domain.settings.impl

import com.example.playlistmaker.domain.settings.interactor.SettingsSharedPrefsInteractor
import com.example.playlistmaker.domain.settings.repository.SettingsSharedPrefsRepository

class SettingsSharedPrefsInteractorImpl(
    private val repository: SettingsSharedPrefsRepository
): SettingsSharedPrefsInteractor {

    override fun readSettings(): Boolean {
        return repository.readSettings()
    }

    override fun writeSettings(darkTheme: Boolean) {
        repository.writeSettings(darkTheme)
    }

   override fun switchTheme(darkThemeEnabled: Boolean) {
        repository.switchTheme(darkThemeEnabled)
    }
}