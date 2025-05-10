package com.example.playlistmaker.ui.media.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityMediaBinding
import com.example.playlistmaker.ui.media.presentation.MediatekaPageAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MediaActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMediaBinding
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fragmentContainer.adapter = MediatekaPageAdapter(this)

        tabMediator = TabLayoutMediator(binding.tabs, binding.fragmentContainer) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.favorite)
                1 -> tab.text = getString(R.string.playlists)
            }
        }
        tabMediator.attach()

        binding.backArrow.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }
}