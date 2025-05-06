package com.example.playlistmaker.di

import android.content.Context
import android.media.MediaPlayer
import com.example.playlistmaker.data.search.network.NetworkClient
import com.example.playlistmaker.data.search.network.NetworkClientImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val HISTORY_KEY = "history_key"
private const val THEME_KEY = "theme_key"

val dataModule = module{

    single(named(HISTORY_KEY)){
        androidApplication().getSharedPreferences(
            HISTORY_KEY, Context.MODE_PRIVATE
        )
    }

    single(named(THEME_KEY)){
        androidApplication().getSharedPreferences(
            THEME_KEY, Context.MODE_PRIVATE
        )
    }

    factory {
        MediaPlayer()
    }

    single<NetworkClient> {
        NetworkClientImpl(androidApplication())
    }
}