package com.example.playlistmaker.ui.media.presentation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.playlistmaker.ui.media.activity.MediaActivity
import com.example.playlistmaker.ui.media.fragments.FavoriteTracksFragment
import com.example.playlistmaker.ui.media.fragments.PlaylistsFragment

class MediatekaPageAdapter(hostActivity: MediaActivity): FragmentStateAdapter(hostActivity){

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) FavoriteTracksFragment() else PlaylistsFragment()
    }
}