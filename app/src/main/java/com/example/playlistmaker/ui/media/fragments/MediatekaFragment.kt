package com.example.playlistmaker.ui.media.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playlistmaker.R
import com.example.playlistmaker.binding.BindingFragment
import com.example.playlistmaker.databinding.MediaFragmentBinding
import com.example.playlistmaker.ui.media.presentation.MediatekaPageAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MediatekaFragment : BindingFragment<MediaFragmentBinding>() {

    private lateinit var tabMediator: TabLayoutMediator

    companion object {
        fun newInstance(): MediatekaFragment = MediatekaFragment()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): MediaFragmentBinding {
        return MediaFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            val adapter = MediatekaPageAdapter(hostFragment = this)
        binding.fragmentContainer.adapter = adapter

        tabMediator = TabLayoutMediator(binding.tabs, binding.fragmentContainer) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.favorite)
                1 -> tab.text = getString(R.string.playlists)
            }
        }
        tabMediator.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator.detach()
    }
}