package com.example.playlistmaker.data.search.impl

import com.example.playlistmaker.data.search.models.SongsResponse
import com.example.playlistmaker.data.search.network.NetworkClient
import com.example.playlistmaker.domain.models.Resourse
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.search.repository.SearchRepository
import com.example.playlistmaker.domain.models.ErrorType

class SearchRepositoryImpl(private val networkClient: NetworkClient): SearchRepository {

    override fun searchSong(expression: String): Resourse<List<SongData>> {
        val songResponse = networkClient.searchSong(expression)

        return if(songResponse is SongsResponse){
            if (songResponse.results.isEmpty()) {
                Resourse.Error(ErrorType.EmptyResponse)
            } else {
                Resourse.Success(songResponse.results)
            }
        } else {
            when (songResponse.resultCode) {
                -1 -> Resourse.Error(ErrorType.NoInternetConnection)
                500 -> Resourse.Error(ErrorType.ServerError)
                else -> Resourse.Error(ErrorType.InvalidRequest)
            }
        }
    }
}