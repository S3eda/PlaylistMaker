package com.example.playlistmaker

import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

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
            val songPageIntent = Intent(holder.itemView.context,SongPageActivity::class.java)
            val json = Gson().toJson(data[position])
            songPageIntent.putExtra("SONG_INFORMATION", json)
            holder.itemView.context.startActivity(songPageIntent)
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
                    searchHistory.reverse()
                    searchHistory.add(data[position])
                    searchHistory.reverse()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}