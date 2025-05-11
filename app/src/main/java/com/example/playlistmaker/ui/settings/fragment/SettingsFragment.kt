package com.example.playlistmaker.ui.settings.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playlistmaker.R
import com.example.playlistmaker.binding.BindingFragment
import com.example.playlistmaker.databinding.SettingsFragmentBinding
import com.example.playlistmaker.ui.settings.view_model.SettingsFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BindingFragment<SettingsFragmentBinding>() {

    companion object{
        fun newInstance(): SettingsFragment = SettingsFragment()
    }

    private val viewModel: SettingsFragmentViewModel by viewModel()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): SettingsFragmentBinding {
        return SettingsFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getIsDarkThemeDarkLiveData().observe(requireActivity()) { isDefaultThemeDark ->
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
    }
}
