package com.example.playlistmaker.domain.settings.interactor

interface SettingsSharedPrefsInteractor {
    fun readSettings(): Boolean
    fun writeSettings(darkTheme: Boolean)
    fun switchTheme(darkThemeEnabled: Boolean)
}