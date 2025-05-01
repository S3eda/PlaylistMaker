package com.example.playlistmaker.creator

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.example.playlistmaker.data.player.PlayerRepositoryImpl
import com.example.playlistmaker.data.search.impl.SearchRepositoryImpl
import com.example.playlistmaker.data.sharing.impl.SettingsExternalNavigationRepositoryImpl
import com.example.playlistmaker.data.search.history.impl.HistorySharedPrefsRepositoryImpl
import com.example.playlistmaker.data.settings.impl.SettingsSharedPrefsRepositoryImpl
import com.example.playlistmaker.data.search.network.NetworkClientImpl
import com.example.playlistmaker.data.search.network.NetworkClient
import com.example.playlistmaker.domain.player.impl.PlayerInteractorImpl
import com.example.playlistmaker.domain.sharing.impl.SettingsExternalNavigationInteractorImpl
import com.example.playlistmaker.domain.search.history.impl.HistorySharedPrefsInteractorImpl
import com.example.playlistmaker.domain.settings.impl.SettingsSharedPrefsInteractorImpl
import com.example.playlistmaker.domain.player.interactor.PlayerInteractor
import com.example.playlistmaker.domain.sharing.interactor.SettingsExternalNavigationInteractor
import com.example.playlistmaker.domain.search.history.interactor.HistorySharedPrefsInteractor
import com.example.playlistmaker.domain.settings.interactor.SettingsSharedPrefsInteractor
import com.example.playlistmaker.domain.player.repository.PlayerRepository
import com.example.playlistmaker.domain.search.repository.SearchRepository
import com.example.playlistmaker.domain.sharing.repository.SettingsExternalNavigationRepository
import com.example.playlistmaker.domain.search.history.repository.HistorySharedPrefsRepository
import com.example.playlistmaker.domain.settings.repository.SettingsSharedPrefsRepository
import com.example.playlistmaker.domain.search.useCase.SearchSongUseCase

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

    private fun getHistorySharedPrefsRepository(): HistorySharedPrefsRepository {
        return HistorySharedPrefsRepositoryImpl(getHistorySharedPrefs(context))
    }

    fun provideHistorySharedPrefsInteractor(): HistorySharedPrefsInteractor {
        return HistorySharedPrefsInteractorImpl(getHistorySharedPrefsRepository())
    }

    private fun getSettingsSharedPrefsRepository(): SettingsSharedPrefsRepository {
        return SettingsSharedPrefsRepositoryImpl(getSettingsSharedPrefs(context))
    }

    fun provideSettingsSharedPrefsInteractor(): SettingsSharedPrefsInteractor {
        return SettingsSharedPrefsInteractorImpl(getSettingsSharedPrefsRepository())
    }

    fun initContext(cont: Application){
        context = cont
    }

    private fun getPlayerRepository(): PlayerRepository {
        return PlayerRepositoryImpl()
    }

    fun providePlayerInteractor(): PlayerInteractor {
        return PlayerInteractorImpl(getPlayerRepository())
    }

    fun provideSearchUseCase(): SearchSongUseCase {
        return SearchSongUseCase(getSearchRepository())
    }

    private fun getSearchRepository(): SearchRepository {
        return SearchRepositoryImpl(provideNetworkClient())
    }

    private fun provideNetworkClient(): NetworkClient {
        return NetworkClientImpl()
    }

    private fun getSettingsExternalNavigationRepository(
    ): SettingsExternalNavigationRepository {
        return SettingsExternalNavigationRepositoryImpl(context)
    }

    fun provideSettingsExternalNavigationInteractor(
    ): SettingsExternalNavigationInteractor {
        return SettingsExternalNavigationInteractorImpl(
            getSettingsExternalNavigationRepository()
        )
    }
}