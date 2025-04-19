package com.example.playlistmaker.ui.presentation

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Parcel
import android.os.Parcelable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.Creator
import com.example.playlistmaker.domain.models.SongData
import com.example.playlistmaker.ui.SearchActivity
import com.example.playlistmaker.ui.SongPageActivity
import com.google.gson.Gson

class SongsAdapter (
    val onClickAction: (SongData) -> Unit
) : RecyclerView.Adapter<PlaylistViewHolder>(){

    companion object{
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private var data = listOf<SongData>()
    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(parent)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
            holder.bind(data[position])
            holder.itemView.setOnClickListener {
                if (clickDebounce()) {
                    onClickAction.invoke(data[position])
                    notifyDataSetChanged()
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

    fun setItem(list: List<SongData>){
        data = list
        notifyDataSetChanged()
    }
}