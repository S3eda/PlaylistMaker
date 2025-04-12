package com.example.playlistmaker.data.impl

import com.example.playlistmaker.data.models.SongsResponse
import com.example.playlistmaker.data.network.NetworkClient
import com.example.playlistmaker.domain.models.Resourse
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.repository.SearchRepository

class SearchRepositoryImpl(private val networkClient: NetworkClient): SearchRepository{

    override fun searchSong(expression: String): Resourse<List<SongData>> {
        val songResponse = networkClient.searchSong(expression)

        return if(songResponse is SongsResponse){
            Resourse.Success(songResponse.results)
        } else {
            Resourse.Error("Error")
        }
    }
}