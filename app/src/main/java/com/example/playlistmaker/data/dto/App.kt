package com.example.playlistmaker.data.dto
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.Creator
import com.example.playlistmaker.domain.interactor.SettingsSharedPrefsInteractor

class App:Application() {

    var darkTheme = false
    lateinit var settingsInteractor: SettingsSharedPrefsInteractor

    override fun onCreate() {
        super.onCreate()

        Creator.initContext(this)
        settingsInteractor = Creator.provideSettingsSharedPrefsInteractor()

        darkTheme = settingsInteractor.readSettings()
        switchTheme(darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(settingsInteractor.switchTheme(darkThemeEnabled))
    }
}