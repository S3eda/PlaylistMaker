package com.example.playlistmaker

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SongsAdapter (
    private val data: List<SongData>
) : RecyclerView.Adapter<PlaylistViewHolder>(){

    companion object{
        var searchHistory = mutableListOf<SongData>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(parent)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener{
            when{
                searchHistory.size != 0 && data[position] in searchHistory-> {

                    val subList = mutableListOf<SongData>()
                    subList.add(data[position])
                    searchHistory.remove(data[position])
                    subList.addAll(searchHistory)
                    searchHistory.clear()
                    searchHistory.addAll(subList)
                    subList.clear()
                    notifyDataSetChanged()
                }
                searchHistory.size == 10 -> {
                    searchHistory.removeAt(9)
                    searchHistory.reverse()
                    searchHistory.add(data[position])
                    searchHistory.reverse()
                }
                else -> {
                    searchHistory.add(data[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun changeList(list1:MutableList<SongData>, list2:MutableList<SongData>){
        var i = 1
        for(element in list1) {
            list2.add(list1[list1.size - i])
            i = i+1
        }
    }
}