package com.example.playlistmaker

import android.content.SharedPreferences
import com.example.playlistmaker.data.dto.App.Companion.HISTORY_KEY
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.presentation.SongsAdapter
import com.google.gson.Gson

class SearchHistory (val sharedPreferences: SharedPreferences) {

    /*fun readHistoryList():Array<SongData>{
        val json = sharedPreferences.getString(HISTORY_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<SongData>::class.java)
    }

    fun writeHistoryList(list: Array<SongData>) {
        val json = Gson().toJson(list)
        sharedPreferences.edit()
            .putString(HISTORY_KEY, json)
            .apply()
    }

    fun listForAdapter(list1: MutableList<SongData>, list2: MutableList<SongData>):MutableList<SongData>{
        if (list1.isEmpty() && list2.isNotEmpty()
            ||
            list1.size < list2.size){
            return list2
        }
        if (list2.isNotEmpty() &&
            list1[0].trackId != list2[0].trackId){
            return list2
        }
        return list1
    }

    fun completionOfSongAdapterSearchHistory (ex: SearchHistory){
        if(SongsAdapter.searchHistory.isEmpty()) {
            for (element in ex.readHistoryList().toMutableList()) {
                SongsAdapter.searchHistory.add(element)
            }
        }
    }

    fun clearHistory (){
        SongsAdapter.searchHistory.clear()
        sharedPreferences.edit()
            .clear()
            .apply()
    }*/
}