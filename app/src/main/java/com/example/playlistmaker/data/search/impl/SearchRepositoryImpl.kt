package com.example.playlistmaker.data.search.impl

import com.example.playlistmaker.data.search.models.SongsResponse
import com.example.playlistmaker.data.search.network.NetworkClient
import com.example.playlistmaker.domain.models.Resourse
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.search.repository.SearchRepository

class SearchRepositoryImpl(private val networkClient: NetworkClient): SearchRepository {

    override fun searchSong(expression: String): Resourse<List<SongData>> {
        val songResponse = networkClient.searchSong(expression)

        return if(songResponse is SongsResponse){
            Resourse.Success(songResponse.results)
        } else {
            Resourse.Error("Error")
        }
    }
}