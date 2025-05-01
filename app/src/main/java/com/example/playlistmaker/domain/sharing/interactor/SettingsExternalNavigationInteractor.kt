package com.example.playlistmaker.domain.sharing.interactor

import android.content.Intent

interface SettingsExternalNavigationInteractor {
    fun share(link: String)
    fun support(email: String, subjectForDev: String, massageForDev: String)
    fun userAgreements(link: String)
}