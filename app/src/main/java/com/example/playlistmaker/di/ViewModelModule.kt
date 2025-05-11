package com.example.playlistmaker.di

import com.example.playlistmaker.ui.media.view_model.FavoriteViewModel
import com.example.playlistmaker.ui.media.view_model.PlaylistsViewModel
import com.example.playlistmaker.ui.search.view_model.SearchFragmentViewModel
import com.example.playlistmaker.ui.settings.view_model.SettingsFragmentViewModel
import com.example.playlistmaker.ui.song_page.view_model.SongPageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{

    viewModel{
        SongPageViewModel(get(), get())
    }

    viewModel{
        FavoriteViewModel()
    }

    viewModel{
        PlaylistsViewModel()
    }

    viewModel{
        SearchFragmentViewModel(get(), get())
    }

    viewModel{
        SettingsFragmentViewModel(get(), get())
    }
}