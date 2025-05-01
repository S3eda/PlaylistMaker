package com.example.playlistmaker.ui.search

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.PlaylistItemBinding
import com.example.playlistmaker.domain.models.SongData

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
        val layoutInspector = LayoutInflater.from(parent.context)
        return PlaylistViewHolder(PlaylistItemBinding.inflate(layoutInspector, parent, false))
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