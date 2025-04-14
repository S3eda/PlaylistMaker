package com.example.playlistmaker.data.impl

import android.content.SharedPreferences
import com.example.playlistmaker.Creator
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.repository.HistorySharedPrefsRepository
import com.google.gson.Gson

class HistorySharedPrefsRepositoryImpl(private val sharedPrefs: SharedPreferences): HistorySharedPrefsRepository {

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
}