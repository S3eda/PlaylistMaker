package com.example.playlistmaker.ui.media.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.ui.media.models.PlaylistsFragmentScreenState

class PlaylistsViewModel:ViewModel() {

    private val screenStateLiveData: MutableLiveData<PlaylistsFragmentScreenState> =
        MutableLiveData(PlaylistsFragmentScreenState.Empty)

    fun screenStateObserve(): LiveData<PlaylistsFragmentScreenState> = screenStateLiveData
}