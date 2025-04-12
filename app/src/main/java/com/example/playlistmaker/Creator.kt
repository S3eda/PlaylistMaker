package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.data.impl.PlayerRepositoryImpl
import com.example.playlistmaker.data.impl.SearchRepositoryImpl
import com.example.playlistmaker.data.impl.SongSearchHistoryRepositoryImpl
import com.example.playlistmaker.data.network.NetworkClientImpl
import com.example.playlistmaker.data.network.NetworkClient
import com.example.playlistmaker.domain.impl.PlayerInteractorImpl
import com.example.playlistmaker.domain.impl.SongSearchHistoryInteractorImpl
import com.example.playlistmaker.domain.interactor.PlayerInteractor
import com.example.playlistmaker.domain.interactor.SongSearchHistoryInteractor
import com.example.playlistmaker.domain.repository.PlayerRepository
import com.example.playlistmaker.domain.repository.SearchRepository
import com.example.playlistmaker.domain.repository.SongSearchHistoryRepository
import com.example.playlistmaker.domain.useCase.SearchSongUseCase

object Creator {

    lateinit var context: Application

    private fun getSongSearchHistoryRepository(): SongSearchHistoryRepository{
        return SongSearchHistoryRepositoryImpl(context)
    }

    fun provideSongSearchHistoryInteractor(): SongSearchHistoryInteractor{
        return SongSearchHistoryInteractorImpl(getSongSearchHistoryRepository())
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
}