package com.example.playlistmaker.di

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

private const val HISTORY_KEY = "history_key"
private const val THEME_KEY = "theme_key"

val repositoryModule = module{

    single<SettingsSharedPrefsRepository>{
        SettingsSharedPrefsRepositoryImpl( sharedPrefs = get(named(THEME_KEY)))
    }

    single<SettingsExternalNavigationRepository>{
        SettingsExternalNavigationRepositoryImpl(androidApplication())
    }

    single<SearchRepository>{
        SearchRepositoryImpl(networkClient = get())
    }

    single<HistorySharedPrefsRepository>{
        HistorySharedPrefsRepositoryImpl(sharedPrefs = get(named(HISTORY_KEY)))
    }

    factory <PlayerRepository>{
        PlayerRepositoryImpl(get())
    }
}