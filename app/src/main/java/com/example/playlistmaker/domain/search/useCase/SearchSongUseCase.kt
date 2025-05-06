package com.example.playlistmaker.domain.search.useCase

import com.example.playlistmaker.domain.Consumer.Consumer
import com.example.playlistmaker.domain.Consumer.ConsumerData
import com.example.playlistmaker.domain.models.Resourse
import com.example.playlistmaker.domain.models.SongData
import java.util.concurrent.Executors
import com.example.playlistmaker.domain.search.repository.SearchRepository
import com.example.playlistmaker.domain.models.ErrorType

class SearchSongUseCase(private val searchRepository: SearchRepository) {

    private val executor = Executors.newSingleThreadExecutor()

    fun execute(expression: String, consumer: Consumer<List<SongData>>) {
        executor.execute{
            val songListResourse = searchRepository.searchSong(expression)
            when(songListResourse){
                is Resourse.Error -> {
                    when(songListResourse.errorType){
                        is ErrorType.NoInternetConnection -> {
                            consumer.consume(
                                ConsumerData
                                    .Error(ErrorType.NoInternetConnection)
                            )
                        }
                        is ErrorType.InvalidRequest -> {
                            consumer.consume(
                                ConsumerData
                                    .Error(ErrorType.InvalidRequest)
                            )
                        }
                        is ErrorType.EmptyResponse -> {
                            consumer.consume(
                                ConsumerData
                                    .Error(ErrorType.EmptyResponse)
                            )
                        }
                        is ErrorType.ServerError -> {
                            consumer.consume(
                                ConsumerData
                                    .Error(ErrorType.ServerError)
                            )
                        }
                    }
                }
                is Resourse.Success -> {
                    consumer.consume(ConsumerData.Data(songListResourse.data))
                }
            }
        }
    }
}
