package com.example.playlistmaker.data.impl

import android.content.Intent
import android.net.Uri
import com.example.playlistmaker.domain.repository.SettingsExternalNavigationRepository

class SettingsExternalNavigationRepositoryImpl(): SettingsExternalNavigationRepository {

    override fun share(link: String): Intent {
        val shareIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse( "mailto:")
            putExtra(Intent.EXTRA_TEXT, link)
        }
        return shareIntent
    }

    override fun userAgreements(link: String): Intent {
        val agreementIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        return agreementIntent
    }

    override fun support(email: String, subjectForDev: String, massageForDev: String): Intent {
        val supportIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse( "mailto:$email")
            putExtra(Intent.EXTRA_EMAIL, email)
            putExtra(Intent.EXTRA_SUBJECT, subjectForDev)
            putExtra(Intent.EXTRA_TEXT, massageForDev)
        }
        return supportIntent
    }
}