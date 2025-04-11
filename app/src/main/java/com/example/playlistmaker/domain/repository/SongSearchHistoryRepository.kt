package com.example.playlistmaker.domain.repository

import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.data.models.SongDataResponse
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.presentation.SongsAdapter
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter

interface SongSearchHistoryRepository {
    fun readHistory():Array<SongData>
    fun writeHistory(data: Array<SongData>)
    fun clearHistory()
}