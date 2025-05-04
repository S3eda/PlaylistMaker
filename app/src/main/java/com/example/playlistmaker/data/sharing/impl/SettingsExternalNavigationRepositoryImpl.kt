package com.example.playlistmaker.data.sharing.impl

import android.app.Application
import android.content.Intent
import android.net.Uri
import com.example.playlistmaker.domain.sharing.repository.SettingsExternalNavigationRepository

class SettingsExternalNavigationRepositoryImpl(private val application: Application): SettingsExternalNavigationRepository {

    override fun share(link: String){
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
        }
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        application.startActivity(shareIntent)
    }

    override fun userAgreements(link: String){
        val agreementIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        agreementIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        application.startActivity(agreementIntent)
    }

    override fun support(email: String, subjectForDev: String, massageForDev: String){
        val supportIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse( "mailto:$email")
            putExtra(Intent.EXTRA_EMAIL, email)
            putExtra(Intent.EXTRA_SUBJECT, subjectForDev)
            putExtra(Intent.EXTRA_TEXT, massageForDev)
        }
        supportIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        application.startActivity(supportIntent)
    }
}