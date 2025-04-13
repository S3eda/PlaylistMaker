package com.example.playlistmaker.data.dto

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.Creator

class App:Application() {

    companion object {
        const val PLAYLISTMAKER_THEME_MODE = "playlistmaker_theme_mode"
        const val THEME_KEY = "theme_key"
    }

    var darkTheme = false

    override fun onCreate() {
        super.onCreate()

        Creator.initContext(this)

        val sharedPrefs = Creator.getSharedPrefs(this, THEME_KEY)
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