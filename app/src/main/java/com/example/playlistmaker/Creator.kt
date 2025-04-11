package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.data.impl.PlayerRepositoryImpl
import com.example.playlistmaker.data.impl.SongSearchHistoryRepositoryImpl
import com.example.playlistmaker.domain.impl.PlayerInteractorImpl
import com.example.playlistmaker.domain.impl.SongSearchHistoryInteractorImpl
import com.example.playlistmaker.domain.interactor.PlayerInteractor
import com.example.playlistmaker.domain.interactor.SongSearchHistoryInteractor
import com.example.playlistmaker.domain.repository.PlayerRepository
import com.example.playlistmaker.domain.repository.SongSearchHistoryRepository

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
}