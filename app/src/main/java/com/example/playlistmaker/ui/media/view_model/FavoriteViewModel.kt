package com.example.playlistmaker.ui.media.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.ui.media.models.FavoritesFragmentScreenState


class FavoriteViewModel : ViewModel() {

    private val screenStateLiveData: MutableLiveData<FavoritesFragmentScreenState> =
        MutableLiveData(FavoritesFragmentScreenState.Empty)

    fun screenStateObserve(): LiveData<FavoritesFragmentScreenState> = screenStateLiveData
}