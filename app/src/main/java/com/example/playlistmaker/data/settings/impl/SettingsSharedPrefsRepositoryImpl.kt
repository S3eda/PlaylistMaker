package com.example.playlistmaker.data.settings.impl

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.domain.settings.repository.SettingsSharedPrefsRepository

class SettingsSharedPrefsRepositoryImpl(
    private val sharedPrefs: SharedPreferences
): SettingsSharedPrefsRepository {

    companion object{
        const val THEME_KEY = "theme_key"
    }


    override fun readSettings(): Boolean {
        return sharedPrefs.getBoolean(THEME_KEY, false)
    }

    override fun writeSettings(darkTheme: Boolean) {
        sharedPrefs
            .edit()
            .putBoolean(THEME_KEY, darkTheme)
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