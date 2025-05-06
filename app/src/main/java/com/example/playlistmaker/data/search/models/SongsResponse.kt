package com.example.playlistmaker.data.search.models

import com.example.playlistmaker.domain.models.SongData

class SongsResponse (val results: List<SongData>) : NetworkResponse()
