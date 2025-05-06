package com.example.playlistmaker.domain.sharing.impl

import com.example.playlistmaker.domain.sharing.interactor.SettingsExternalNavigationInteractor
import com.example.playlistmaker.domain.sharing.repository.SettingsExternalNavigationRepository

class SettingsExternalNavigationInteractorImpl(
    private val repository: SettingsExternalNavigationRepository
): SettingsExternalNavigationInteractor {

    override fun share(link: String){
        repository.share(link)
    }

    override fun userAgreements(link: String){
        repository.userAgreements(link)
    }

    override fun support(email: String, subjectForDev: String, massageForDev: String){
        repository.support(email, subjectForDev, massageForDev)
    }
}