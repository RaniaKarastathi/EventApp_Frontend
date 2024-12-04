package com.example.eventapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EventsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_page)

        // Get the selected city from the intent
        val selectedCity = intent.getStringExtra("selectedCity")
        // Get the selected category from the intent
        val selectedCategory = intent.getStringExtra("category")

        // check if selectedCity or selectedCategory is null
        when {
            !selectedCity.isNullOrEmpty() -> {
                Toast.makeText(this, "City: $selectedCity", Toast.LENGTH_SHORT).show()
                fetchEventsByCity(selectedCity)
            }
            !selectedCategory.isNullOrEmpty() -> {
                Toast.makeText(this, "Category: $selectedCategory", Toast.LENGTH_SHORT).show()
                fetchEventsByCategory(selectedCategory)
            }
            else -> {
                Toast.makeText(this, "No city or category provided!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun fetchEventsByCity(city: String) {

    }

    private fun fetchEventsByCategory(category: String) {

    }

}