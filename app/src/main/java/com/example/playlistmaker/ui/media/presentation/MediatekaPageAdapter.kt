package com.example.playlistmaker.ui.media.presentation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.playlistmaker.ui.media.fragments.FavoriteTracksFragment
import com.example.playlistmaker.ui.media.fragments.PlaylistsFragment

class MediatekaPageAdapter(hostFragment: Fragment): FragmentStateAdapter(hostFragment){

    private val fragmentsList = listOf<Fragment>(
        FavoriteTracksFragment.newInstance(),
        PlaylistsFragment.newInstance()
    )

    override fun getItemCount(): Int {
        return fragmentsList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentsList[position]
    }
}