package com.example.eventapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private lateinit var eventTitle: TextView
private lateinit var eventDescription: TextView
private lateinit var eventDate: TextView
private lateinit var eventLocation: TextView
private lateinit var bookButton: Button

private lateinit var backButton: Button
private lateinit var homeButton: Button
class SpecificEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_event)

        eventTitle = findViewById(R.id.eventTitle)
        eventDescription = findViewById(R.id.eventDescription)
        eventDate = findViewById(R.id.eventDate)
        eventLocation = findViewById(R.id.eventLocation)
        bookButton = findViewById(R.id.bookButton)

        backButton = findViewById(R.id.backButton)
        homeButton = findViewById(R.id.homeButton)

        // Get data from intent
        val title = intent.getStringExtra("eventTitle") ?: "Τίτλος Event"
        val description = intent.getStringExtra("eventDescription") ?: "Περιγραφή Event"
        val date = intent.getStringExtra("eventDate") ?: "Ημερομηνία Event"
        val location = intent.getStringExtra("eventLocation") ?: "Τοποθεσία Event"

        // show data
        eventTitle.text = title
        eventDescription.text = description
        eventDate.text = date
        eventLocation.text = location

        // OnClick for reservation
        bookButton.setOnClickListener {
            bookTicket(title)
        }

        // OnClick for backButton
        backButton.setOnClickListener {
            finish()
        }

        // Onclick for HomeButton
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun bookTicket(eventTitle: String) {
        Toast.makeText(this, "Κρατήθηκε το εισιτήριο για το: $eventTitle", Toast.LENGTH_SHORT).show()
    }
}