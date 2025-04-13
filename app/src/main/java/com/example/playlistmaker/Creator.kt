package com.example.playlistmaker

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.example.playlistmaker.data.impl.PlayerRepositoryImpl
import com.example.playlistmaker.data.impl.SearchRepositoryImpl
import com.example.playlistmaker.data.impl.SettingsExternalNavigationRepositoryImpl
import com.example.playlistmaker.data.impl.SharedPrefsRepositoryImpl
import com.example.playlistmaker.data.network.NetworkClientImpl
import com.example.playlistmaker.data.network.NetworkClient
import com.example.playlistmaker.domain.impl.PlayerInteractorImpl
import com.example.playlistmaker.domain.impl.SettingsExternalNavigationInteractorImpl
import com.example.playlistmaker.domain.impl.SharedPrefsInteractorImpl
import com.example.playlistmaker.domain.interactor.PlayerInteractor
import com.example.playlistmaker.domain.interactor.SettingsExternalNavigationInteractor
import com.example.playlistmaker.domain.interactor.SharedPrefsInteractor
import com.example.playlistmaker.domain.repository.PlayerRepository
import com.example.playlistmaker.domain.repository.SearchRepository
import com.example.playlistmaker.domain.repository.SettingsExternalNavigationRepository
import com.example.playlistmaker.domain.repository.SharedPrefsRepository
import com.example.playlistmaker.domain.useCase.SearchSongUseCase

object Creator {

    lateinit var context: Application

    fun getSharedPrefs(context: Context, key: String):SharedPreferences{
        return context.getSharedPreferences(key, MODE_PRIVATE)
    }

    private fun getSharedPrefsRepository(key: String): SharedPrefsRepository{
        return SharedPrefsRepositoryImpl(context, key)
    }

    fun provideSharedPrefsInteractor(key: String): SharedPrefsInteractor{
        return SharedPrefsInteractorImpl(getSharedPrefsRepository(key))
    }

    fun initContext(cont: Application){
        context = cont
    }

    private fun getPlayerRepository(): PlayerRepository{
        return PlayerRepositoryImpl()
    }

    fun providePlayerInteractor(): PlayerInteractor{
        return PlayerInteractorImpl(getPlayerRepository())
    }

    fun provideSearchUseCase(): SearchSongUseCase{
        return SearchSongUseCase(getSearchRepository())
    }

    private fun getSearchRepository(): SearchRepository{
        return SearchRepositoryImpl(provideNetworkClient())
    }

    private fun provideNetworkClient(): NetworkClient{
        return NetworkClientImpl()
    }

    private fun getSettingsExternalNavigationRepository(
    ): SettingsExternalNavigationRepository{
        return SettingsExternalNavigationRepositoryImpl()
    }

    fun provideSettingsExternalNavigationInteractor(
    ):SettingsExternalNavigationInteractor{
        return SettingsExternalNavigationInteractorImpl(
            getSettingsExternalNavigationRepository()
        )
    }
}