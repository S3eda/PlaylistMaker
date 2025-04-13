package com.example.playlistmaker.data.impl

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.domain.repository.SharedPrefsRepository
import com.google.gson.Gson

class SharedPrefsRepositoryImpl(private val context: Context, private val key: String): SharedPrefsRepository {

    private val someSharedPrefs = context.getSharedPreferences(key, MODE_PRIVATE)

    override fun readSongHistory(): Array<SongData> {
        val json = someSharedPrefs.getString(key, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<SongData>::class.java)
    }

    override fun writeSongHistory(data: Array<SongData>) {
        val json = Gson().toJson(data)
        someSharedPrefs.edit()
            .clear()
            .putString(key, json)
            .apply()
    }

    override fun clearSongHistory() {
        someSharedPrefs.edit()
            .clear()
            .apply()
    }

    override fun readSettings(): Boolean {
        return someSharedPrefs.getBoolean(key, false)
    }

    override fun writeSettings(darkTheme: Boolean) {
        someSharedPrefs
            .edit()
            .putBoolean(key, darkTheme)
            .apply()
    }
}