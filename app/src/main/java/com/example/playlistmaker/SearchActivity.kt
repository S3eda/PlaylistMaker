package com.example.playlistmaker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    var saveSearchText:String = SOME_TEXT
    private val baseUrl = "https://itunes.apple.com"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val searchAPI = retrofit.create(SearchAPI::class.java)

    private lateinit var searchText: EditText
    private lateinit var somethingWrongText: TextView
    private lateinit var somethingWrongImage: ImageView
    private lateinit var refreshButton: Button

    private var songsList = mutableListOf<SongData>()
    private val songsAdapter = SongsAdapter(songsList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val backImage = findViewById<ImageView>(R.id.back_search)
        backImage.setOnClickListener{
            finish()
        }

        searchText = findViewById(R.id.searchText)
        somethingWrongText = findViewById(R.id.something_wrong_text)
        somethingWrongImage = findViewById(R.id.something_wrong_image)
        refreshButton = findViewById(R.id.refresh_button)

        if (savedInstanceState != null) {
            saveSearchText = savedInstanceState.getString(SEARCH_STRING, SOME_TEXT)
            searchText.setText(saveSearchText)
        }

        val clearButton = findViewById<Button>(R.id.clearButton)
        clearButton.setOnClickListener {
            searchText.setText("")
            songsList.clear()
            songsAdapter.notifyDataSetChanged()
            somethingWrongText.visibility = View.GONE
            somethingWrongImage.visibility = View.GONE
            refreshButton.visibility = View.GONE
        }

        refreshButton.setOnClickListener{
            somethingWrongText.visibility = View.GONE
            somethingWrongImage.visibility = View.GONE
            refreshButton.visibility = View.GONE
            searchTrack()
        }

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearButton.visibility = clearSearchButtonVisibility(s)
                saveSearchText = s.toString()
            }
        }
        searchText.addTextChangedListener(searchTextWatcher)

        val searchList = findViewById<RecyclerView>(R.id.search_recyclerView)
        searchList.adapter = songsAdapter

            searchText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchTrack()
            }
                false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_STRING, saveSearchText)
    }

    companion object{
        const val SEARCH_STRING = "SEARCH_STRING"
        const val SOME_TEXT = ""
    }

    private fun clearSearchButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

        private fun searchTrack (){
        searchAPI
            .search(searchText.text.toString())
            .enqueue(object: Callback<SongsResponse> {
                override fun onResponse(
                    call: Call<SongsResponse>,
                    response: Response<SongsResponse>
                ) {
                    if (response.code() == 200) {
                        songsList.clear()
                        if (response.body()?.results?.isNotEmpty() == true) {
                            songsList.addAll(response.body()?.results!!)
                            songsAdapter.notifyDataSetChanged()
                        }
                        if (songsList.isEmpty()){
                            showMessage(getString(R.string.empty_search_result), "", false)
                        }
                    } else {
                        showMessage(getString(R.string.trouble_with_internet), "", true)
                    }
                }

                override fun onFailure(call: Call<SongsResponse>, t: Throwable) {
                    showMessage(getString(R.string.trouble_with_internet), "", true)
                }
            })
    }

    private fun showMessage(text: String, additionalMessage: String, internetError: Boolean) {
        if (internetError) {
            somethingWrongText.visibility = View.VISIBLE
            somethingWrongImage.visibility = View.VISIBLE
            refreshButton.visibility = View.VISIBLE
            songsList.clear()
            songsAdapter.notifyDataSetChanged()
            somethingWrongText.text = text
            somethingWrongImage.setImageResource(R.drawable.trouble_with_internet)
        } else {
            if (text.isNotEmpty()) {
                somethingWrongText.visibility = View.VISIBLE
                somethingWrongImage.visibility = View.VISIBLE
                songsList.clear()
                songsAdapter.notifyDataSetChanged()
                somethingWrongText.text = text
                somethingWrongImage.setImageResource(R.drawable.empty_search)
                if (additionalMessage.isNotEmpty()) {
                    Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}