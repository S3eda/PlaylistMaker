package com.example.playlistmaker.di

import com.example.playlistmaker.Constants
import com.example.playlistmaker.data.player.PlayerRepositoryImpl
import com.example.playlistmaker.data.search.history.impl.HistorySharedPrefsRepositoryImpl
import com.example.playlistmaker.data.search.impl.SearchRepositoryImpl
import com.example.playlistmaker.data.settings.impl.SettingsSharedPrefsRepositoryImpl
import com.example.playlistmaker.data.sharing.impl.SettingsExternalNavigationRepositoryImpl
import com.example.playlistmaker.domain.player.repository.PlayerRepository
import com.example.playlistmaker.domain.search.history.repository.HistorySharedPrefsRepository
import com.example.playlistmaker.domain.search.repository.SearchRepository
import com.example.playlistmaker.domain.settings.repository.SettingsSharedPrefsRepository
import com.example.playlistmaker.domain.sharing.repository.SettingsExternalNavigationRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module{

    single<SettingsSharedPrefsRepository>{
        SettingsSharedPrefsRepositoryImpl( sharedPrefs = get(named(Constants.THEME_KEY)))
    }

    single<SettingsExternalNavigationRepository>{
        SettingsExternalNavigationRepositoryImpl(androidApplication())
    }

    single<SearchRepository>{
        SearchRepositoryImpl(networkClient = get())
    }

    single<HistorySharedPrefsRepository>{
        HistorySharedPrefsRepositoryImpl(sharedPrefs = get(named(Constants.HISTORY_KEY)))
    }

    single<PlayerRepository>{
        PlayerRepositoryImpl(get())
    }
}