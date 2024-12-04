package com.example.eventapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide


private lateinit var eventImageView: ImageView
private lateinit var name: TextView
private lateinit var description: TextView
private lateinit var date: TextView
private lateinit var venue: TextView
private lateinit var price: TextView
private lateinit var availability: TextView
private lateinit var ticketTypes: TextView
private lateinit var duration: TextView
private lateinit var bookButton: Button


private lateinit var backButton: Button
private lateinit var homeButton: Button
class SpecificEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_event)

        eventImageView = findViewById(R.id.eventImageView)
        name = findViewById(R.id.eventNameTextView)
        description = findViewById(R.id.eventDescriptionTextView)
        date = findViewById(R.id.eventDateTextView)
        venue = findViewById(R.id.venueTextView)
        price = findViewById(R.id.priceTextView)
        availability = findViewById(R.id.availabilityTextView)
        ticketTypes = findViewById(R.id.ticketTypesTextView)
        duration = findViewById(R.id.durationTextView)

        bookButton = findViewById(R.id.bookButton)
        backButton = findViewById(R.id.backButton)
        homeButton = findViewById(R.id.homeButton)

        // Get data from intent
        val imageUrl = intent.getStringExtra("imageUrl")
        val eventName = intent.getStringExtra("name") ?: "Unknown Name"
        val eventDescription = intent.getStringExtra("description") ?: "Unknown Description"
        val eventDate = intent.getStringExtra("date") ?: "Unknown Date"
        val venueName = intent.getStringExtra("venue") ?: "Unknown Venue"
        val availabilityStatus = intent.getStringExtra("availability") ?: "Unknown Availability"
        val ticketTypesList = intent.getStringExtra("ticket_types") ?: "No Ticket Types Available"
        val ticketPrice = intent.getStringExtra("price") ?: "0"
        val eventDuration = intent.getStringExtra("duration") ?: "Unknown Duration"

         //Load image using Glide
        Glide.with(this)
           .load(imageUrl)
           .into(eventImageView)

        // show data
        name.text = eventName
        description.text = eventDescription
        date.text = eventDate
        venue.text = venueName
        price.text = ticketPrice
        availability.text =  availabilityStatus
        ticketTypes.text = ticketTypesList
        duration.text = eventDuration

        // OnClick for reservation
        bookButton.setOnClickListener {
            bookTicket(eventName)
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