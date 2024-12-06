package com.example.eventapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsActivity : AppCompatActivity() {

    private lateinit var eventsAdapter: EventsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_page)

        // Get the selected city and category from the intent
        val selectedCity = intent.getStringExtra("selectedCity")
        val selectedCategory = intent.getStringExtra("category")


        // Ρύθμιση RecyclerView
        recyclerView = findViewById(R.id.eventsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        eventsAdapter = EventsAdapter(emptyList()) // Αρχικοποίηση με κενή λίστα
        recyclerView.adapter = eventsAdapter

        // Ρύθμιση του TextView με το location/κατηγορία
        val locationText: TextView = findViewById(R.id.locationText)
        locationText.text = when {
            !selectedCity.isNullOrEmpty() -> "Events στην $selectedCity"
            !selectedCategory.isNullOrEmpty() -> "Events στην κατηγορία: $selectedCategory"
            else -> "Δεν βρεθηκαν Event"
        }

        // Ανάλογα με την επιλογή του χρήστη, κάνε το κατάλληλο API call
        when {
            !selectedCity.isNullOrEmpty() -> fetchEventsByCity(selectedCity)
            !selectedCategory.isNullOrEmpty() -> fetchEventsByCategory(selectedCategory)
            else -> {
                Toast.makeText(this, "No city or category provided!", Toast.LENGTH_SHORT).show()
            }
        }

        // Ρύθμιση του listener για κλικ στο event
        eventsAdapter.setOnItemClickListener { event ->
            val intent = Intent(this, SpecificEventActivity::class.java)
            intent.putExtra("eventType", event.eventType)
            intent.putExtra("customId", event.customId)
            intent.putExtra("eventName", event.name)
            intent.putExtra("eventDate", event.date)
            intent.putExtra("artist", event.artist)
            intent.putExtra("artist2", event.artist2)
            intent.putExtra("cast", event.cast)
            intent.putExtra("genre", event.genre)
            intent.putExtra("imageLink", event.imageLink)
            intent.putExtra("location", event.location)
            intent.putExtra("startTime", event.startTime)
            intent.putExtra("venue", event.venue)
            intent.putExtra("availability", event.availability)
            intent.putExtra("ticket_types", event.ticketTypes)
            intent.putExtra("length", event.length)
            intent.putExtra("eventPromo", event.eventPromo)
            intent.putExtra("organizerName", event.organizerName)
            intent.putExtra("ticketColor", event.ticketColor)
            startActivity(intent)
        }
    }

    private fun fetchEventsByCity(city: String) {
        RetrofitInstance.api.getEventsByLocation(city).enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    val events = response.body()
                    Log.d("EventsActivity", "Fetched events: $events") // Έλεγχος απόκρισης
                    if (!events.isNullOrEmpty()) {
                        eventsAdapter.updateEvents(events)
                    } else {
                        Toast.makeText(this@EventsActivity, "No events found for $city", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("EventsActivity", "Error: ${response.code()}") // Έλεγχος σφαλμάτων
                    Toast.makeText(this@EventsActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                Log.e("EventsActivity", "API Failure: ${t.message}")
                Toast.makeText(this@EventsActivity, "Failed to fetch events: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchEventsByCategory(category: String) {
        RetrofitInstance.api.getEventsByCategory(category).enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    val events = response.body()
                    Log.d("EventsActivity", "Fetched events: $events") // Έλεγχος απόκρισης
                    if (!events.isNullOrEmpty()) {
                        eventsAdapter.updateEvents(events)
                    } else {
                        Toast.makeText(this@EventsActivity, "No events found for $category", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("EventsActivity", "Error: ${response.code()}") // Έλεγχος σφαλμάτων
                    Toast.makeText(this@EventsActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                Log.e("EventsActivity", "API Failure: ${t.message}")
                Toast.makeText(this@EventsActivity, "Failed to fetch events: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
