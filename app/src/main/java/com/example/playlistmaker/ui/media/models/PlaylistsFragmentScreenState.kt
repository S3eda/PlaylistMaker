package com.example.playlistmaker.ui.media.models

import com.example.playlistmaker.ui.media.view_model.PlaylistsViewModel

sealed class PlaylistsFragmentScreenState {
    data object Empty : PlaylistsFragmentScreenState()
    data object Content : PlaylistsFragmentScreenState()
}