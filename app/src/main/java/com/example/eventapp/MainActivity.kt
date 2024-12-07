package com.example.eventapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // List of cities
        val cities = listOf(
            "ΑθΗΝΑ", "ΘΕΣΣΑΛΟΝΙΚΗ", "ΠΑΤΡΑ", "ΒΟΛΟΣ", "ΧΑΝΙΑ",
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


        citySearchBar.setOnItemClickListener { parent, view, position, id ->
            // Get the selected city from the AutoCompleteTextView
            val selectedCity = parent.getItemAtPosition(position) as String
            Log.d("MainActivity", "City selected: $selectedCity") // Debugging line

            // Create an Intent to start EventsActivity
            val intent = Intent(this, EventsActivity::class.java)

            // Use 'selectedCity' from the clicked item to determine which city was selected
            when (selectedCity) {
                "ΑΘΗΝΑ" -> intent.putExtra("selectedCity", "athens")
                "ΘΕΣΣΑΛΟΝΙΚΗ" -> intent.putExtra("selectedCity", "thessaloniki")
                "ΠΑΤΡΑ" -> intent.putExtra("selectedCity", "patra")
                "ΒΟΛΟΣ" -> intent.putExtra("selectedCity", "volos")
                "ΧΑΝΙΑ" -> intent.putExtra("selectedCity", "chania")
                else -> {
                    Toast.makeText(this, "Unknown city selected!", Toast.LENGTH_SHORT).show()
                    return@setOnItemClickListener // Exit the listener if an unknown city is selected
                }
            }

            // Debugging line to log the intent extra value
            Log.d("MainActivity", "Intent starting for city: ${intent.getStringExtra("selectedCity")}")

            // Start EventsActivity
            startActivity(intent)
        }



        val standupcomedyIcon: Button = findViewById(R.id.standupcomedyIcon)
        val musicIcon: Button = findViewById(R.id.musicIcon)
        val theaterIcon: Button = findViewById(R.id.theaterIcon)
        val artIcon: Button = findViewById(R.id.artIcon)
        val footballIcon: Button = findViewById(R.id.footballIcon)
        val basketballIcon: Button = findViewById(R.id.basketballIcon)
        val volleyballIcon: Button = findViewById(R.id.volleyballIcon)

        // OnClickListener for standupcomedyIcon
        standupcomedyIcon.setOnClickListener {
            val intent = Intent(this, EventsActivity::class.java)
            intent.putExtra("category", "standupcomedy")
            startActivity(intent)
        }


        // OnClickListener for musicIcon
        musicIcon.setOnClickListener {
            val intent = Intent(this, EventsActivity::class.java)
            intent.putExtra("category", "music")
            startActivity(intent)
        }

        // OnClickListener for theaterIcon
        theaterIcon.setOnClickListener {
            val intent = Intent(this, EventsActivity::class.java)
            intent.putExtra("category", "theater")
            startActivity(intent)
        }

        // OnClickListener for artIcon
        artIcon.setOnClickListener {
            val intent = Intent(this, EventsActivity::class.java)
            intent.putExtra("category", "artgallery")
            startActivity(intent)
        }

        // OnClickListener for footballIcon
        footballIcon.setOnClickListener {
            val intent = Intent(this, EventsActivity::class.java)
            intent.putExtra("category", "football")
            startActivity(intent)
        }

        // OnClickListener for basketballIcon
        basketballIcon.setOnClickListener {
            val intent = Intent(this, EventsActivity::class.java)
            intent.putExtra("category", "basketball")
            startActivity(intent)
        }

        // OnClickListener for volleyballIcon
        volleyballIcon.setOnClickListener {
            val intent = Intent(this, EventsActivity::class.java)
            intent.putExtra("category", "volleyball")
            startActivity(intent)
        }

        // RecyclerView
        val featuredEvents = mutableListOf<Event>()
        val featuredEventsRecyclerView: RecyclerView = findViewById(R.id.featuredEventsRecyclerView)
        featuredEventsRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val adapter2 = FeaturedEventsAdapter(featuredEvents) { event ->
            event?.let {
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
                val tickets: ArrayList<Ticket> = ArrayList(event.tickets ?: emptyList())
                intent.putExtra("tickets", tickets)
                startActivity(intent)
            }
        }
        featuredEventsRecyclerView.adapter = adapter2

        RetrofitInstance.api.getEvents().enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    response.body()?.let { events ->
                        featuredEvents.clear()
                        featuredEvents.addAll(events)
                        adapter2.notifyDataSetChanged()
                    } ?: Log.e("API", "Response body is null")
                } else {
                    Log.e("API", "Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                Log.e("API", "Failure: ${t.message}")
                Toast.makeText(this@MainActivity, "Αποτυχία φόρτωσης εκδηλώσεων", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
