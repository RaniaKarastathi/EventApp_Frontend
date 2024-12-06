package com.example.eventapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SpecificEventActivity : AppCompatActivity() {

    private lateinit var name: TextView
    private lateinit var description: TextView
    private lateinit var date: TextView
    private lateinit var venue: TextView
    private lateinit var price: TextView
    private lateinit var availability: TextView
    //private lateinit var ticketTypes: TextView
    private lateinit var duration: TextView
    private lateinit var bookButton: Button


    private lateinit var backButton: Button
    private lateinit var homeButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_event)

        name = findViewById(R.id.eventNameTextView)
        description = findViewById(R.id.eventDescriptionTextView)
        date = findViewById(R.id.eventDateTextView)
        venue = findViewById(R.id.venueTextView)
        price = findViewById(R.id.priceTextView)
        availability = findViewById(R.id.availabilityTextView)
        //ticketTypes = findViewById(R.id.ticketTypesTextView)
        duration = findViewById(R.id.durationTextView)

        bookButton = findViewById(R.id.bookButton)
        backButton = findViewById(R.id.backButton)
        homeButton = findViewById(R.id.homeButton)

        val eventId = intent.getStringExtra("eventId")
        if (eventId != null) {
            fetchEventDetails(eventId)
        } else {
            loadEventFromIntent()
        }

        bookButton.setOnClickListener {
            val eventName = name.text.toString()
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

    private fun loadEventFromIntent() {
        name.text = intent.getStringExtra("name") ?: "Unknown Name"
        description.text = "Περιγραφή: ${intent.getStringExtra("description") ?: "Άγνωστη"}"
        date.text = "Ημερομηνία: ${intent.getStringExtra("date") ?: "Άγνωστη"}"
        venue.text = "Τοποθεσία: ${intent.getStringExtra("venue") ?: "Άγνωστη"}"
        price.text = "Τιμή: ${intent.getDoubleExtra("price", -1.0).takeIf { it != -1.0 }?.toString() ?: "Μη διαθέσιμη"}"
        availability.text = "Διαθεσιμότητα: ${intent.getStringExtra("availability") ?: "Άγνωστη"}"
        //ticketTypes.text = "Τύποι Εισιτηρίων: ${intent.getStringExtra("ticket_types") ?: "Μη διαθέσιμο"}"
        duration.text = "Διάρκεια: ${intent.getStringExtra("duration") ?: "Άγνωστη"}"
    }

    private fun fetchEventDetails(eventId: String) {
        RetrofitInstance.api.getEventById(eventId).enqueue(object : Callback<Event> {
            override fun onResponse(call: Call<Event>, response: Response<Event>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        populateEventDetails(it)
                    }
                } else {
                    Log.e("SpecificEventActivity", "Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Event>, t: Throwable) {
                Log.e("SpecificEventActivity", "API Failure: ${t.message}")
            }
        })
    }

    private fun populateEventDetails(event: Event) {
        name.text = event.name
        description.text = "Περιγραφή: ${event.description ?: "Άγνωστη"}"
        date.text = "Ημερομηνία: ${event.date ?: "Άγνωστη"}"
        venue.text = "Τοποθεσία: ${event.venue ?: "Άγνωστη"}"
        price.text = "Τιμή: ${event.price?.toString() ?: "Μη διαθέσιμη"}"
        availability.text = "Διαθεσιμότητα: ${event.availability ?: "Άγνωστη"}"
       // ticketTypes.text = "Τύποι Εισιτηρίων: ${event.ticketTypes ?: "Μη διαθέσιμο"}"
        duration.text = "Διάρκεια: ${event.duration ?: "Άγνωστη"}"
    }

    private fun bookTicket(eventTitle: String) {
        Toast.makeText(this, "Κρατήθηκε το εισιτήριο για το: $eventTitle", Toast.LENGTH_SHORT).show()

    }
}