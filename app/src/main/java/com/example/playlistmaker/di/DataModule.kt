package com.example.playlistmaker.di

import android.content.Context
import android.media.MediaPlayer
import com.example.playlistmaker.Constants
import com.example.playlistmaker.data.search.network.NetworkClient
import com.example.playlistmaker.data.search.network.NetworkClientImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module{

    single(named(Constants.HISTORY_KEY)){
        androidApplication().getSharedPreferences(
            Constants.HISTORY_KEY, Context.MODE_PRIVATE
        )
    }

    single(named(Constants.THEME_KEY)){
        androidApplication().getSharedPreferences(
            Constants.THEME_KEY, Context.MODE_PRIVATE
        )
    }

    factory {
        MediaPlayer()
    }

    single<NetworkClient> {
        NetworkClientImpl(androidApplication())
    }
}