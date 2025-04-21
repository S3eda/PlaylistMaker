package com.example.playlistmaker.domain.repository

interface SettingsSharedPrefsRepository {
    fun readSettings(): Boolean
    fun writeSettings(darkTheme: Boolean)
    fun switchTheme(darkThemeEnabled: Boolean): Int
}