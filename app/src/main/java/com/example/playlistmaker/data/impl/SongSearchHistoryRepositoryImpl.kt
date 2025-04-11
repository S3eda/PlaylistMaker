package com.example.playlistmaker.data.impl

import android.content.Context
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.data.dto.App
import com.example.playlistmaker.data.dto.App.Companion.HISTORY_KEY
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.repository.SongSearchHistoryRepository
import com.example.playlistmaker.presentation.SongsAdapter
import com.google.gson.Gson

class SongSearchHistoryRepositoryImpl(private val cont: Context): SongSearchHistoryRepository {
    val historySharedPrefs = cont.getSharedPreferences(App.HISTORY_LIST, MODE_PRIVATE)

    override fun readHistory(): Array<SongData> {
        val json = historySharedPrefs.getString(HISTORY_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<SongData>::class.java)
    }

    override fun writeHistory(data: Array<SongData>) {
        val json = Gson().toJson(data)
        historySharedPrefs.edit()
            .clear()
            .putString(HISTORY_KEY, json)
            .apply()
    }

    override fun clearHistory() {
        historySharedPrefs.edit()
            .clear()
            .apply()
    }
}