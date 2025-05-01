package com.example.playlistmaker.domain.search.useCase

import com.example.playlistmaker.domain.Consumer.Consumer
import com.example.playlistmaker.domain.Consumer.ConsumerData
import com.example.playlistmaker.domain.models.Resourse
import com.example.playlistmaker.domain.models.SongData
import java.util.concurrent.Executors
import com.example.playlistmaker.domain.search.repository.SearchRepository

class SearchSongUseCase(private val searchRepository: SearchRepository) {

    private val executor = Executors.newSingleThreadExecutor()

    fun execute(expression: String, consumer: Consumer<List<SongData>>) {
        executor.execute{
            val songListResourse = searchRepository.searchSong(expression)
            when(songListResourse){
                is Resourse.Error -> {
                    consumer.consume(
                        ConsumerData
                            .Error("")
                    )
                }
                is Resourse.Success -> {
                    consumer.consume(ConsumerData.Data(songListResourse.data))
                }
            }
        }
    }
}
