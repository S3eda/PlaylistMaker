package com.example.playlistmaker.domain.useCase

import android.os.Handler
import android.os.Looper
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.Consumer.Consumer
import com.example.playlistmaker.domain.Consumer.ConsumerData
import com.example.playlistmaker.domain.models.Resourse
import com.example.playlistmaker.domain.models.SongData
import java.util.concurrent.Executors
import com.example.playlistmaker.domain.repository.SearchRepository

//private val handler = Handler(Looper.getMainLooper())
//private val SEARCH_DEBOUNCE_DELAY = 2000L


class SearchSongUseCase(private val searchRepository: SearchRepository) {

    private val executor = Executors.newSingleThreadExecutor()
    //private val searchRunnable = Runnable { execute()}

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
