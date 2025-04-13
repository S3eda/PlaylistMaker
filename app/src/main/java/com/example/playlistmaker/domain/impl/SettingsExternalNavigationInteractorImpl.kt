package com.example.playlistmaker.domain.impl

import android.content.Intent
import com.example.playlistmaker.domain.interactor.SettingsExternalNavigationInteractor
import com.example.playlistmaker.domain.repository.SettingsExternalNavigationRepository

class SettingsExternalNavigationInteractorImpl(
    private val repository: SettingsExternalNavigationRepository
): SettingsExternalNavigationInteractor {

    override fun share(link: String): Intent {
        return repository.share(link)
    }

    override fun userAgreements(link: String): Intent {
        return repository.userAgreements(link)
    }

    override fun support(subjectForDev: String, massageForDev: String): Intent {
        return repository.support(subjectForDev, massageForDev)
    }
}