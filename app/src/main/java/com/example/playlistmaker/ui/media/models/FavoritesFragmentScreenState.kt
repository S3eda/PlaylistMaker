package com.example.playlistmaker.ui.media.models

sealed class FavoritesFragmentScreenState {
    data object Empty : FavoritesFragmentScreenState()
    data object Content : FavoritesFragmentScreenState()
}