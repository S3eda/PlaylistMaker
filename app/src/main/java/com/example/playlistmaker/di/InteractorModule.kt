package com.example.playlistmaker.di

import com.example.playlistmaker.data.search.history.impl.HistorySharedPrefsRepositoryImpl
import com.example.playlistmaker.domain.player.impl.PlayerInteractorImpl
import com.example.playlistmaker.domain.player.interactor.PlayerInteractor
import com.example.playlistmaker.domain.search.history.impl.HistorySharedPrefsInteractorImpl
import com.example.playlistmaker.domain.search.history.interactor.HistorySharedPrefsInteractor
import com.example.playlistmaker.domain.search.useCase.SearchSongUseCase
import com.example.playlistmaker.domain.settings.impl.SettingsSharedPrefsInteractorImpl
import com.example.playlistmaker.domain.settings.interactor.SettingsSharedPrefsInteractor
import com.example.playlistmaker.domain.sharing.impl.SettingsExternalNavigationInteractorImpl
import com.example.playlistmaker.domain.sharing.interactor.SettingsExternalNavigationInteractor
import org.koin.dsl.module

val interactorModule = module {

    factory <PlayerInteractor> {
        PlayerInteractorImpl(get())
    }

    single<SearchSongUseCase>{
        SearchSongUseCase(searchRepository = get())
    }

    single<HistorySharedPrefsInteractor>{
        HistorySharedPrefsInteractorImpl(get())
    }

    single<SettingsSharedPrefsInteractor> {
        SettingsSharedPrefsInteractorImpl(get())
    }

    single<SettingsExternalNavigationInteractor>{
        SettingsExternalNavigationInteractorImpl(get())
    }
}