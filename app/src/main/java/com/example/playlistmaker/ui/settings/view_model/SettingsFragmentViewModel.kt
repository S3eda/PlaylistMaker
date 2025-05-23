package com.example.playlistmaker.ui.settings.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.settings.interactor.SettingsSharedPrefsInteractor
import com.example.playlistmaker.domain.sharing.interactor.SettingsExternalNavigationInteractor
import com.example.playlistmaker.util.SingleEventLiveData

class SettingsFragmentViewModel (
    private val externalNavigationInteractor: SettingsExternalNavigationInteractor,
    private val settingsInteractor: SettingsSharedPrefsInteractor,
) : ViewModel(){

    private val isDarkThemeDarkLiveData = SingleEventLiveData<Boolean>()

    init {
        isDarkThemeDarkLiveData.value = settingsInteractor.readSettings()
    }

    fun getIsDarkThemeDarkLiveData(): LiveData<Boolean> = isDarkThemeDarkLiveData

    fun changeTheme(isDarkTheme: Boolean) {
        settingsInteractor.writeSettings(isDarkTheme)
        settingsInteractor.switchTheme(isDarkTheme)
    }

    fun share(link: String) {
        externalNavigationInteractor.share(link)
    }

    fun support(email: String, subjectForDev: String, massageForDev: String) {
        externalNavigationInteractor.support(email, subjectForDev, massageForDev)
    }

    fun userAgreement(link: String) {
        externalNavigationInteractor.userAgreements(link)
    }
}