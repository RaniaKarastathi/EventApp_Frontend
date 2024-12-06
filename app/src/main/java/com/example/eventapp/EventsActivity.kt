package com.example.eventapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        RetrofitInstance.api.getEventsByLocation(city).enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    val events = response.body()
                    if (events != null) {
                        for (event in events) {
                            println("Event Name: ${event.name}, Date: ${event.date}")
                        }
                    }
                } else {
                    Toast.makeText(this@EventsActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                Toast.makeText(this@EventsActivity, "Failed to fetch events: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun fetchEventsByCategory(category: String) {
        RetrofitInstance.api.getEventsByCategory(category).enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    val events = response.body()
                    events?.let {
                        // Ενημερώστε τα δεδομένα της RecyclerView
                    }
                } else {
                    Log.e("EventsActivity", "Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                Log.e("EventsActivity", "API Failure: ${t.message}")
            }
        })
    }

}