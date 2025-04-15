package com.example.playlistmaker

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.example.playlistmaker.data.impl.PlayerRepositoryImpl
import com.example.playlistmaker.data.impl.SearchRepositoryImpl
import com.example.playlistmaker.data.impl.SettingsExternalNavigationRepositoryImpl
import com.example.playlistmaker.data.impl.HistorySharedPrefsRepositoryImpl
import com.example.playlistmaker.data.impl.SettingsSharedPrefsRepositoryImpl
import com.example.playlistmaker.data.network.NetworkClientImpl
import com.example.playlistmaker.data.network.NetworkClient
import com.example.playlistmaker.domain.impl.PlayerInteractorImpl
import com.example.playlistmaker.domain.impl.SettingsExternalNavigationInteractorImpl
import com.example.playlistmaker.domain.impl.HistorySharedPrefsInteractorImpl
import com.example.playlistmaker.domain.impl.SettingsSharedPrefsInteractorImpl
import com.example.playlistmaker.domain.interactor.PlayerInteractor
import com.example.playlistmaker.domain.interactor.SettingsExternalNavigationInteractor
import com.example.playlistmaker.domain.interactor.HistorySharedPrefsInteractor
import com.example.playlistmaker.domain.interactor.SettingsSharedPrefsInteractor
import com.example.playlistmaker.domain.repository.PlayerRepository
import com.example.playlistmaker.domain.repository.SearchRepository
import com.example.playlistmaker.domain.repository.SettingsExternalNavigationRepository
import com.example.playlistmaker.domain.repository.HistorySharedPrefsRepository
import com.example.playlistmaker.domain.repository.SettingsSharedPrefsRepository
import com.example.playlistmaker.domain.useCase.SearchSongUseCase

object Creator {

    lateinit var context: Application
    const val HISTORY_KEY = "history_key"
    const val THEME_KEY = "theme_key"

    fun getHistorySharedPrefs(context: Context):SharedPreferences{
        return context.getSharedPreferences(HISTORY_KEY, MODE_PRIVATE)
    }

    fun getSettingsSharedPrefs(context: Context):SharedPreferences{
        return context.getSharedPreferences(THEME_KEY, MODE_PRIVATE)
    }

    private fun getHistorySharedPrefsRepository(): HistorySharedPrefsRepository{
        return HistorySharedPrefsRepositoryImpl(getHistorySharedPrefs(context))
    }

    fun provideHistorySharedPrefsInteractor(): HistorySharedPrefsInteractor{
        return HistorySharedPrefsInteractorImpl(getHistorySharedPrefsRepository())
    }

    private fun getSettingsSharedPrefsRepository(): SettingsSharedPrefsRepository{
        return SettingsSharedPrefsRepositoryImpl(getSettingsSharedPrefs(context))
    }

    fun provideSettingsSharedPrefsInteractor():SettingsSharedPrefsInteractor{
        return SettingsSharedPrefsInteractorImpl(getSettingsSharedPrefsRepository())
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