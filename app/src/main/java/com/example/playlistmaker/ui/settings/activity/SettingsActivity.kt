package com.example.playlistmaker.ui.settings.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.data.settings.dto.App
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.ui.settings.view_model.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    //private lateinit var viewModel: SettingsViewModel
    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*viewModel = ViewModelProvider(
            this,
            SettingsViewModel.getSettingsModelFactory()
        )[SettingsViewModel::class.java]*/

        viewModel.getIsDarkThemeDarkLiveData().observe(this) { isDefaultThemeDark ->
            binding.themeSwitch.isChecked = isDefaultThemeDark
        }

        binding.themeSwitch.setOnCheckedChangeListener { _, checked ->
            viewModel.changeTheme(checked)
        }

        binding.share.setOnClickListener {
            viewModel.share(getString(R.string.practicum))
        }

        binding.support.setOnClickListener{
            viewModel.support(
                getString(R.string.dev_email),
                getString(R.string.for_dev),
                getString(R.string.thanks_dev)
            )
        }

        binding.usersAgreement.setOnClickListener{
            viewModel.userAgreement(getString(R.string.users_agreement_link))
        }

        binding.back.setOnClickListener {
            finish()
        }
    }
}