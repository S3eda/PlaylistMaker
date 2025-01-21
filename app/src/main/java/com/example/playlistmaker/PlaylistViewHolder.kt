package com.example.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale

class PlaylistViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)) {
    private val vhTrackName: TextView = itemView.findViewById(R.id.track_name)
    private val vhArtistName: TextView = itemView.findViewById(R.id.artist_name)
    private val vhTrackTime: TextView = itemView.findViewById(R.id.track_time)
    private val vhArtwork: ImageView = itemView.findViewById(R.id.artwork)

    fun bind(item: SongData){
        val artwork = item.artworkUrl100

        vhTrackName.text = item.trackName
        vhArtistName.text = item.artistName
        vhTrackTime.text = item.trackTimeMillis
        Glide.with(itemView)
            .load(artwork)
            .placeholder(R.drawable.internet_error_icon)
            .transform(RoundedCorners(2))
            .into(vhArtwork)
    }
}
