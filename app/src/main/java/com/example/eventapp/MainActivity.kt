package com.example.eventapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // List of cities
        val cities = listOf(
            "Athens", "Thessaloniki", "Patra", "Volos", "Hania",
        )

        // Find the AutoCompleteTextView in the layout
        val citySearchBar = findViewById<AutoCompleteTextView>(R.id.citySearchBar)

        // Create an ArrayAdapter to populate the AutoCompleteTextView
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, R.id.dropdownItemText, cities)

        // Set the adapter to the AutoCompleteTextView
        citySearchBar.setAdapter(adapter)

        // Show the dropdown list when the user taps the search bar
        citySearchBar.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                citySearchBar.showDropDown() // Automatically show dropdown when focused
            }
        }

        // Handle item selection (if you want to do something when a city is selected)
        citySearchBar.setOnItemClickListener { parent, view, position, id ->
            val selectedCity = parent.getItemAtPosition(position) as String

            // Pass the selected city to the next activity
            val intent = Intent(this, EventsPage::class.java)
            intent.putExtra("selectedCity", selectedCity) // Pass the selected city name
            startActivity(intent)
        }

        val musicIcon: ImageView = findViewById(R.id.musicIcon)
        val theaterIcon: ImageView = findViewById(R.id.theaterIcon)
        val artIcon: ImageView = findViewById(R.id.artIcon)
        val sportsIcon: ImageView = findViewById(R.id.sportsIcon)
    }

}

