package com.example.playlistmaker.ui.settings.view_model

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.domain.settings.interactor.SettingsSharedPrefsInteractor
import com.example.playlistmaker.domain.sharing.interactor.SettingsExternalNavigationInteractor
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.data.SingleEventLiveData

class SettingsViewModel(
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

    companion object {
        fun getSettingsModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(
                    Creator.provideSettingsExternalNavigationInteractor(),
                    Creator.provideSettingsSharedPrefsInteractor()
                )
            }
        }
    }
}
