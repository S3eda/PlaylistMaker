package com.example.playlistmaker.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.Creator
import com.example.playlistmaker.data.dto.App
import com.example.playlistmaker.R
import com.google.android.material.switchmaterial.SwitchMaterial



class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val settingsNavigation = Creator.provideSettingsExternalNavigationInteractor()
        val sharedPrefsInteractor = Creator.provideSettingsSharedPrefsInteractor()

        val backImage = findViewById<ImageView>(R.id.back)
        backImage.setOnClickListener{
            finish()
        }

        val share = findViewById<TextView>(R.id.share)
        share.setOnClickListener{
            startActivity(Intent
                .createChooser(
                    settingsNavigation
                        .share(getString(R.string.practicum)),
                    null)
            )
        }

        val support = findViewById<TextView>(R.id.support)
        support.setOnClickListener{
            startActivity(
                settingsNavigation.support(
                    getString(R.string.dev_email),
                    getString(R.string.for_dev),
                    getString(R.string.thanks_dev)
                )
            )
        }

        val usersAgreement = findViewById<TextView>(R.id.usersAgreement)
        usersAgreement.setOnClickListener{
            startActivity(
                settingsNavigation
                    .userAgreements(
                        getString(R.string.users_agreement_link)
                    )
            )
        }

        val changeTheme = findViewById<SwitchMaterial>(R.id.theme_switch)
        val darkTheme = sharedPrefsInteractor.readSettings()
        changeTheme.isChecked = darkTheme
        changeTheme.setOnCheckedChangeListener { _, checked ->
            sharedPrefsInteractor.writeSettings(checked)
            (applicationContext as App).switchTheme(checked)
        }
    }
}