package com.example.eventapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.StringBuilder

//const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //getMyData();

        // List of cities
        val cities = listOf(
            "Αθήνα", "Θεσσαλονίκη", "Πάτρα", "Βόλος", "Χανιά",
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
            val selectedCity = parent.getItemAtPosition(position) as String

            // Pass the selected city to the next activity
            val intent = Intent(this, EventsActivity::class.java)
            intent.putExtra("selectedCity", selectedCity) // Pass the selected city name
            startActivity(intent)
        }

        val musicIcon: ImageView = findViewById(R.id.musicIcon)
        val theaterIcon: ImageView = findViewById(R.id.theaterIcon)
        val artIcon: ImageView = findViewById(R.id.artIcon)
        val sportsIcon: ImageView = findViewById(R.id.sportsIcon)


        // OnClickListener for musicIcon
        musicIcon.setOnClickListener {
            val intent = Intent(this, EventsActivity::class.java)
            intent.putExtra("category", "Music")
            startActivity(intent)
        }

        // OnClickListener for theaterIcon
        theaterIcon.setOnClickListener {
            val intent = Intent(this, EventsActivity::class.java)
            intent.putExtra("category", "Theater")
            startActivity(intent)
        }

        // OnClickListener for artIcon
        artIcon.setOnClickListener {
            val intent = Intent(this, EventsActivity::class.java)
            intent.putExtra("category", "Art")
            startActivity(intent)
        }

        // OnClickListener for sportsIcon
        sportsIcon.setOnClickListener {
            val intent = Intent(this, EventsActivity::class.java)
            intent.putExtra("category", "Sports")
            startActivity(intent)
        }

        val featuredEvents = mutableListOf<Event>()


        val featuredEventsRecyclerView: RecyclerView = findViewById(R.id.featuredEventsRecyclerView)

        featuredEventsRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter2 = FeaturedEventsAdapter(featuredEvents)
        featuredEventsRecyclerView.adapter = adapter2



        //API call
        RetrofitInstance.api.getEvents().enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    response.body()?.let { events ->
                        featuredEvents.clear()
                        featuredEvents.addAll(events)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Log.e("API", "Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                Log.e("API", "Failure: ${t.message}")
            }
        })
    }
}
