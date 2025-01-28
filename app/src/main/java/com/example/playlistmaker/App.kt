package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App:Application() {

    companion object {
        const val PLAYLISTMAKER_THEME_MODE = "playlistmaker_theme_mode"
        const val THEME_KEY = "theme_key"
        const val HISTORY_KEY = "history_key"
        const val HISTORY_LIST = "history_list"
    }

    var darkTheme = false

    override fun onCreate() {
        super.onCreate()
        val sharedPrefs = getSharedPreferences(PLAYLISTMAKER_THEME_MODE, MODE_PRIVATE)
        darkTheme = sharedPrefs.getBoolean(THEME_KEY, false)
        switchTheme(darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}