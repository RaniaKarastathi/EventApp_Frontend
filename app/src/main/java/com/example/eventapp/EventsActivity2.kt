package com.example.eventapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

class EventsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        // Λήψη του location από το Intent
        val location = intent.getStringExtra("location") ?: "Unknown Location"

        // Ρύθμιση του TextView με το location
        val locationText: TextView = findViewById(R.id.locationText)
        locationText.text = "Events in $location"

        // Dummy δεδομένα για events
        val events = listOf(
            "Music Concert at $location",
            "Art Exhibition at $location",
            "Tech Meetup at $location",
            "Food Festival at $location",
            "Charity Marathon at $location"
        )

        // Ρύθμιση του RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.eventsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this) // Κάθετη λίστα
        recyclerView.adapter = EventsAdapter(events) // Σύνδεση Adapter με δεδομένα
    }
}
