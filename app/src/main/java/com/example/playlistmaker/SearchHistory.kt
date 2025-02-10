package com.example.playlistmaker

import android.content.SharedPreferences
import android.view.View
import android.widget.LinearLayout
import com.example.playlistmaker.App.Companion.HISTORY_KEY
import com.google.gson.Gson

class SearchHistory (val sharedPreferences: SharedPreferences) {

    fun readHistoryList():Array<SongData>{
        val json = sharedPreferences.getString(HISTORY_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<SongData>::class.java)
    }

    fun writeHistoryList(list: Array<SongData>) {
        val json = Gson().toJson(list)
        sharedPreferences.edit()
            .putString(HISTORY_KEY, json)
            .apply()
    }

    fun historyVisibility(isEmpty: Boolean, linearLayout: LinearLayout){
        if (isEmpty){
            linearLayout.visibility = View.GONE
        } else {
            linearLayout.visibility = View.VISIBLE
        }
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

    fun clearHistory (linearLayout: LinearLayout){
        SongsAdapter.searchHistory.clear()
        sharedPreferences.edit()
            .clear()
            .apply()
        linearLayout.visibility = View.GONE
    }
}