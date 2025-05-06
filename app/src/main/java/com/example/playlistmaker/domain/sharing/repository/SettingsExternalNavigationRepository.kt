package com.example.playlistmaker.domain.sharing.repository

import android.content.Intent

interface SettingsExternalNavigationRepository {
    fun share(link: String)
    fun support(email: String, subjectForDev: String, massageForDev: String)
    fun userAgreements(link: String)
}