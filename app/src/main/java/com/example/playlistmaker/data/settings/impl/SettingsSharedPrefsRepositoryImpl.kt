package com.example.playlistmaker.data.settings.impl

import android.app.UiModeManager.MODE_NIGHT_NO
import android.app.UiModeManager.MODE_NIGHT_YES
import android.content.SharedPreferences
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.domain.settings.repository.SettingsSharedPrefsRepository

class SettingsSharedPrefsRepositoryImpl(
    private val sharedPrefs: SharedPreferences
): SettingsSharedPrefsRepository {

    override fun readSettings(): Boolean {
        return sharedPrefs.getBoolean(Creator.THEME_KEY, false)
    }

    override fun writeSettings(darkTheme: Boolean) {
        sharedPrefs
            .edit()
            .putBoolean(Creator.THEME_KEY, darkTheme)
            .apply()
    }

    override fun switchTheme(darkThemeEnabled: Boolean): Int {
        if (darkThemeEnabled) {
            return MODE_NIGHT_YES
        } else {
            return MODE_NIGHT_NO
        }
    }
}