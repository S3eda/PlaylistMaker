package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backImage = findViewById<ImageView>(R.id.back)
        backImage.setOnClickListener{
            finish()
        }

        val share = findViewById<TextView>(R.id.share)
        share.setOnClickListener{
            val link: String = getString(R.string.practicum)
            val shareIntent = Intent(Intent.ACTION_SENDTO).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, link)
            }
            startActivity(Intent.createChooser(shareIntent, null))
        }

        val support = findViewById<TextView>(R.id.support)
        support.setOnClickListener{
            Toast.makeText(this@SettingsActivity, "Нажали на usersAgreement!", Toast.LENGTH_SHORT).show()
            val subjectForDev = getString(R.string.for_dev)
            val massageForDev = getString(R.string.thanks_dev)
            val supportIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.dev_email)))
                putExtra(Intent.EXTRA_SUBJECT, subjectForDev)
                putExtra(Intent.EXTRA_TEXT, massageForDev)
            }
            startActivity(supportIntent)
        }

        val usersAgreement = findViewById<TextView>(R.id.usersAgreement)
        usersAgreement.setOnClickListener{
            Toast.makeText(this@SettingsActivity, "Нажали на support!", Toast.LENGTH_SHORT).show()
            val usersAgreementLink = getString(R.string.users_agreement_link)
            val agreementIntent = Intent(Intent.ACTION_VIEW, Uri.parse(usersAgreementLink))
            startActivity(agreementIntent)
        }

        val sharedPrefs = getSharedPreferences(App.PLAYLISTMAKER_THEME_MODE, MODE_PRIVATE)
        val darkTheme = sharedPrefs.getBoolean(App.THEME_KEY, false)
        val changeTheme = findViewById<SwitchMaterial>(R.id.theme_switch)
        changeTheme.isChecked = darkTheme
        changeTheme.setOnClickListener{
            changeTheme.setOnCheckedChangeListener { _, checked ->
                (applicationContext as App).switchTheme(checked)
                sharedPrefs.edit()
                    .putBoolean(App.THEME_KEY, checked)
                    .apply()

            }
        }
    }
}