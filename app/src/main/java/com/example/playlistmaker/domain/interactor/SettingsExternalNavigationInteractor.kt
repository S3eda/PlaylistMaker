package com.example.playlistmaker.domain.interactor

import android.content.Intent

interface SettingsExternalNavigationInteractor {
    fun share(link: String): Intent
    fun support(email: String, subjectForDev: String, massageForDev: String): Intent
    fun userAgreements(link: String): Intent
}