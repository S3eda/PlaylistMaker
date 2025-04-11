package com.example.playlistmaker

import android.app.Application
import android.content.Context
import com.example.playlistmaker.data.impl.SongSearchHistoryRepositoryImpl
import com.example.playlistmaker.domain.impl.SongSearchHistoryInteractorImpl
import com.example.playlistmaker.domain.interactor.SongSearchHistoryInteractor
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
}