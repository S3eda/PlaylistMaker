package com.example.playlistmaker.ui.media.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.playlistmaker.databinding.FavoriteFragmentBinding
import com.example.playlistmaker.ui.media.models.FavoritesFragmentScreenState
import com.example.playlistmaker.ui.media.view_model.FavoriteViewModel
import com.example.playlistmaker.util.BindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTracksFragment: BindingFragment<FavoriteFragmentBinding>() {

    companion object{
        fun newInstance() : FavoriteTracksFragment = FavoriteTracksFragment()
    }

    private val viewModel: FavoriteViewModel by viewModel()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FavoriteFragmentBinding {
        return FavoriteFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.screenStateObserve().observe(viewLifecycleOwner){ state ->
            when(state){
                is FavoritesFragmentScreenState.Empty -> showEmpty()
                is FavoritesFragmentScreenState.Content -> showContent()
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