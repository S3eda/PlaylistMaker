package com.example.playlistmaker.presentation

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.Creator
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.ui.SearchActivity
import com.example.playlistmaker.ui.SongPageActivity
import com.google.gson.Gson

class SongsAdapter (
    private val data: List<SongData>,
    val listRefactoring: () -> List<SongData>
) : RecyclerView.Adapter<PlaylistViewHolder>(){

    companion object{
        var searchHistory = mutableListOf<SongData>()
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(parent)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
            holder.bind(data[position])
            holder.itemView.setOnClickListener {
                if (clickDebounce()) {
                    notifyDataSetChanged()
                    val songPageIntent =
                        Intent(holder.itemView.context, SongPageActivity::class.java)
                    val json = Gson().toJson(data[position])
                    songPageIntent.putExtra("SONG_INFORMATION", json)
                    holder.itemView.context.startActivity(songPageIntent)
                    SearchActivity.track = data[position]
                    searchHistory.clear()
                    searchHistory.addAll(listRefactoring())
                }
            }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }
}