package com.example.playlistmaker.ui.media.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.playlistmaker.databinding.PlaylistsFragmentBinding
import com.example.playlistmaker.ui.media.models.PlaylistsFragmentScreenState
import com.example.playlistmaker.ui.media.view_model.PlaylistsViewModel
import com.example.playlistmaker.util.BindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment: BindingFragment<PlaylistsFragmentBinding>() {

    private val viewModel: PlaylistsViewModel by viewModel()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): PlaylistsFragmentBinding {
        return PlaylistsFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.screenStateObserve().observe(viewLifecycleOwner){ state ->
            when(state){
                is PlaylistsFragmentScreenState.Empty -> showEmpty()
                is PlaylistsFragmentScreenState.Content -> showContent()
            }
        }
    }

    private fun showEmpty(){
        binding.emptyMediatecaImage.isVisible = true
        binding.emptyMediatecaText. isVisible = true
    }

    private fun showContent(){
        binding.emptyMediatecaImage.isVisible = false
        binding.emptyMediatecaText. isVisible = false
    }
}