package com.example.playlistmaker.ui.main

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.media.fragments.MediatekaFragment
import com.example.playlistmaker.ui.search.fragments.SearchScreenFragment
import com.example.playlistmaker.ui.settings.fragment.SettingsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val search = findViewById<Button>(R.id.search_button)
        search.setOnClickListener {
            supportFragmentManager.commit {
                add(R.id.main_fragment, SearchScreenFragment.newInstance())
            }
        }

        val media = findViewById<Button>(R.id.media_button)
        media.setOnClickListener {
            supportFragmentManager.commit {
                add(R.id.main_fragment, MediatekaFragment.newInstance())
            }
        }

        val settings = findViewById<Button>(R.id.settings_button)
        settings.setOnClickListener {
            supportFragmentManager.commit {
                add(R.id.main_fragment, SettingsFragment.newInstance())
            }
        }
    }
}