package com.example.playlistmaker.domain.search.repository

import com.example.playlistmaker.domain.models.Resourse
import com.example.playlistmaker.domain.models.SongData

interface SearchRepository {
    fun searchSong(expression: String): Resourse<List<SongData>>
}