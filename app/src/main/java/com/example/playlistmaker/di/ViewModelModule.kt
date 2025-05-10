package com.example.playlistmaker.di

import com.example.playlistmaker.ui.media.view_model.FavoriteViewModel
import com.example.playlistmaker.ui.media.view_model.PlaylistsViewModel
import com.example.playlistmaker.ui.search.view_model.SearchViewModel
import com.example.playlistmaker.ui.settings.view_model.SettingsViewModel
import com.example.playlistmaker.ui.song_page.view_model.SongPageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{

    viewModel{
        SearchViewModel(searchHistoryInteractor = get(), searchUseCase = get())
    }

    viewModel{
        SettingsViewModel(externalNavigationInteractor = get(), settingsInteractor = get())
    }

    viewModel{
        SongPageViewModel(get(), get())
    }

    viewModel{
        FavoriteViewModel()
    }

    viewModel{
        PlaylistsViewModel()
    }
}