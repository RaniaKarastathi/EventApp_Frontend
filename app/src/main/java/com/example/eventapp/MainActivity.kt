package com.example.eventapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView


class MainActivity : AppCompatActivity() {

    // SearchView for city search
    private lateinit var citySearchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the SearchView
        citySearchView = findViewById(R.id.citySearchView)

        // Listener for submitting the query from the SearchView
        citySearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // When the user submits the query, we proceed to the EventListActivity
                if (!query.isNullOrEmpty()) {
                    performSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Not implemented
                return false
            }
        })
    }
    // Create the Intent for the EventListActivity
    private fun performSearch(city: String) {
        val intent = Intent(this, EventListActivity::class.java).apply {
            putExtra("CITY", city)
        }

        // Start the EventListActivity
        startActivity(intent)
        }
}

