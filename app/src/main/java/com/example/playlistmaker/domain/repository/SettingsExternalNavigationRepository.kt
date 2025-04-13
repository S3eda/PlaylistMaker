package com.example.playlistmaker.domain.repository

import android.content.Intent

interface SettingsExternalNavigationRepository {
    fun share(link: String): Intent
    fun support(subjectForDev: String, massageForDev: String): Intent
    fun userAgreements(link: String): Intent
}