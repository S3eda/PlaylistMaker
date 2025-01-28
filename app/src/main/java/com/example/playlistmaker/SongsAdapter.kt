package com.example.playlistmaker

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SongsAdapter (
    private val data: List<SongData>
) : RecyclerView.Adapter<PlaylistViewHolder>(){

    companion object{
        val searchHistory = mutableListOf<SongData>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(parent)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener{
            searchHistory.reverse()
            when{
                searchHistory.size != 0 && data[position] in searchHistory-> {
                    searchHistory.remove(data[position])
                    searchHistory.add(data[position])
                }
                searchHistory.size == 10 -> {
                    searchHistory.removeAt(0)
                    searchHistory.add(data[position])
                }
                else -> searchHistory.add(data[position])
            }
            searchHistory.reverse()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}