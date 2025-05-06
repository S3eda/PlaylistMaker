package com.example.playlistmaker.data.search.history.impl

import android.content.SharedPreferences
import com.example.playlistmaker.Constants.HISTORY_KEY
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.search.history.repository.HistorySharedPrefsRepository
import com.google.gson.Gson

class HistorySharedPrefsRepositoryImpl(private val sharedPrefs: SharedPreferences):
    HistorySharedPrefsRepository {

    override fun readSongHistory(): Array<SongData> {
        val json = sharedPrefs.getString(HISTORY_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<SongData>::class.java)
    }

    override fun clearSongHistory() {
        sharedPrefs.edit()
            .clear()
            .apply()
    }

    override fun addSongToList (track: SongData){
        val searchHistory = readSongHistory().toMutableList()
        when {
            searchHistory.size != 0 && track in searchHistory -> {
                val subList = mutableListOf<SongData>()
                subList.add(track)
                searchHistory.remove(track)
                subList.addAll(searchHistory)
                searchHistory.clear()
                searchHistory.addAll(subList)
                subList.clear()
                sharedPrefs.edit()
                    .clear()
                    .putString(HISTORY_KEY, Gson().toJson(searchHistory))
                    .apply()
            }

            searchHistory.size == 10 -> {
                searchHistory.removeAt(9)
                searchHistory.reverse()
                searchHistory.add(track)
                searchHistory.reverse()
                sharedPrefs.edit()
                    .clear()
                    .putString(HISTORY_KEY, Gson().toJson(searchHistory))
                    .apply()
            }

            else -> {
                searchHistory.reverse()
                searchHistory.add(track)
                searchHistory.reverse()
                sharedPrefs.edit()
                    .clear()
                    .putString(HISTORY_KEY, Gson().toJson(searchHistory))
                    .apply()
            }
        }
    }
}