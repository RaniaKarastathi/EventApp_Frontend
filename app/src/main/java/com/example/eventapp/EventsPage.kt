package com.example.eventapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EventsPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_page)

        // Get the selected city from the intent
        val selectedCity = intent.getStringExtra("selectedCity")
    }

}