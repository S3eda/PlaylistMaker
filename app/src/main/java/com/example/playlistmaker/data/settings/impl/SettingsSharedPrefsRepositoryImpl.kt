package com.example.playlistmaker.data.settings.impl

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.Constants
import com.example.playlistmaker.domain.settings.repository.SettingsSharedPrefsRepository

class SettingsSharedPrefsRepositoryImpl(
    private val sharedPrefs: SharedPreferences
): SettingsSharedPrefsRepository {

    override fun readSettings(): Boolean {
        return sharedPrefs.getBoolean(Constants.THEME_KEY, false)
    }

    override fun writeSettings(darkTheme: Boolean) {
        sharedPrefs
            .edit()
            .putBoolean(Constants.THEME_KEY, darkTheme)
            .apply()
    }

    override fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}