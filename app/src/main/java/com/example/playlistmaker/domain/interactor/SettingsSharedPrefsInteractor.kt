package com.example.playlistmaker.domain.interactor

interface SettingsSharedPrefsInteractor {
    fun readSettings(): Boolean
    fun writeSettings(darkTheme: Boolean)
    fun switchTheme(darkThemeEnabled: Boolean): Int
}