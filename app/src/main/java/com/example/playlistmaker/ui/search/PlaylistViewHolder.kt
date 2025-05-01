package com.example.playlistmaker.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.PlaylistItemBinding
import com.example.playlistmaker.domain.models.SongData
import java.text.SimpleDateFormat
import java.util.Locale

class PlaylistViewHolder(private val binding: PlaylistItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SongData){

        val artwork = item.artworkUrl100

        binding.trackName.text = item.trackName
        binding.artistName.text = item.artistName
        binding.trackTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(item.trackTimeMillis)
        Glide.with(itemView)
            .load(artwork)
            .placeholder(R.drawable.internet_error_icon)
            .transform(RoundedCorners(2))
            .into(binding.artwork)
    }
}
