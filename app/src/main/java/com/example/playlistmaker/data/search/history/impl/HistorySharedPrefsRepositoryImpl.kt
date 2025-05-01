package com.example.playlistmaker.data.search.history.impl

import android.content.SharedPreferences
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.search.history.repository.HistorySharedPrefsRepository
import com.google.gson.Gson

class HistorySharedPrefsRepositoryImpl(private val sharedPrefs: SharedPreferences):
    HistorySharedPrefsRepository {

    var anyList = mutableListOf<SongData>()

    override fun getHistoryList():MutableList<SongData>{
        return anyList
    }

    override fun readSongHistory(): Array<SongData> {
        val json = sharedPrefs.getString(Creator.HISTORY_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<SongData>::class.java)
    }

    override fun writeSongHistory(data: Array<SongData>) {
        val json = Gson().toJson(data)
        sharedPrefs.edit()
            .clear()
            .putString(Creator.HISTORY_KEY, json)
            .apply()
    }

    override fun clearSongHistory() {
        sharedPrefs.edit()
            .clear()
            .apply()
    }

    override fun fillingListForHistoryAdapter(list1: MutableList<SongData>, list2: MutableList<SongData>) {
        if (list1.isEmpty()) {
            list1.addAll(list2)
        }
    }

    override fun listRefactoring (track: SongData):MutableList<SongData>{
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
                writeSongHistory(searchHistory.toTypedArray())
                return searchHistory.toMutableList()
            }

            searchHistory.size == 10 -> {
                searchHistory.removeAt(9)
                searchHistory.reverse()
                searchHistory.add(track)
                searchHistory.reverse()
                writeSongHistory(searchHistory.toTypedArray())
                return searchHistory.toMutableList()
            }

            else -> {
                searchHistory.reverse()
                searchHistory.add(track)
                searchHistory.reverse()
                writeSongHistory(searchHistory.toTypedArray())
                return searchHistory.toMutableList()
            }
        }
    }
}