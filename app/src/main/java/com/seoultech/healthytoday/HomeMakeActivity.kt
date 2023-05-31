package com.seoultech.healthytoday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.net.Uri
class HomeMakeActivity : AppCompatActivity() {

    private lateinit var searchButton: Button
    private lateinit var editText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_make)

        editText = findViewById(R.id.editText)
        searchButton = findViewById(R.id.searchButton)

        searchButton.setOnClickListener {
            val searchTerm = editText.text.toString().trim()
            if (searchTerm.isNotEmpty()) {
                performYouTubeSearch(searchTerm)
            }
        }
    }

    private fun performYouTubeSearch(searchTerm: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=$searchTerm"))
        startActivity(intent)
    }
}