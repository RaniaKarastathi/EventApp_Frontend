package com.example.eventapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import coil.load
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SpecificEventActivity : AppCompatActivity() {

    private lateinit var name: TextView
    private lateinit var eventPromo: TextView
    private lateinit var date: TextView
    private lateinit var venue: TextView
    //private lateinit var price: TextView
    private lateinit var availability: TextView
    private lateinit var eventType: TextView
    private lateinit var artist: TextView
    //private lateinit var artist2: TextView
    //private lateinit var cast: TextView
    //private lateinit var genre: TextView
    private lateinit var imageLink: ImageView
    private lateinit var location: TextView
    //private lateinit var customId: TextView
    private lateinit var startTime: TextView
    private lateinit var ticketTypes: TextView
    private lateinit var length: TextView
    private lateinit var organizerName: TextView
    private lateinit var ticketColor: TextView
    private lateinit var price: TextView



    private lateinit var bookButton: Button
    private lateinit var homeButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_event)

        name = findViewById(R.id.eventNameTextView)
        eventPromo = findViewById(R.id.eventDescriptionTextView)
        date = findViewById(R.id.eventDateTextView)
        venue = findViewById(R.id.venueTextView)
        availability = findViewById(R.id.availabilityTextView)
        ticketTypes = findViewById(R.id.ticketTypesTextView)
        length = findViewById(R.id.lenghtTextView)

        eventType = findViewById(R.id.eventTypeTextView)
        artist = findViewById(R.id.artistTextView)
        //artist2 = findViewById(R.id.artist2TextView)
        //cast = findViewById(R.id.castTextView)
        //genre = findViewById(R.id.genreTextView)
        imageLink = findViewById(R.id.imageLinkImageView)
        location = findViewById(R.id.locationTextView)
       // customId = findViewById(R.id.customIdTextView)

        startTime = findViewById(R.id.startTimeTextView)
        organizerName = findViewById(R.id.organizerNameTextView)
        ticketColor = findViewById(R.id.ticketColorTextView)
        price = findViewById(R.id.priceTextView)


        bookButton = findViewById(R.id.bookButton)
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


        // Onclick for HomeButton
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun loadEventFromIntent() {
        eventType.text = "Είδος Event: ${intent.getStringExtra("eventType") ?: "Άγνωστη"}"
        name.text = intent.getStringExtra("name") ?: "Unknown Name"
        artist.text = "Καλλιτέχνης: ${intent.getStringExtra("artist") ?: "Άγνωστη"}"
        //artist2.text = intent.getStringExtra("artist2") ?: "Unknown "
        //cast.text = intent.getStringExtra("cast") ?: "Unknown "
        //genre.text = intent.getStringExtra("genre") ?: "Unknown "
        //
        location.text = "Πόλη: ${intent.getStringExtra("city") ?: "Άγνωστη"}"
        //customId.text = intent.getStringExtra("customId") ?: "Unknown "
        date.text = "Ημερομηνία: ${intent.getStringExtra("date") ?: "Άγνωστη"}"
        startTime.text = "Ώρα Έναρξης: ${intent.getStringExtra("startTime") ?: "Άγνωστη"}"
        venue.text = "Τοποθεσία: ${intent.getStringExtra("venue") ?: "Άγνωστη"}"
        availability.text = "Διαθεσιμότητα: ${intent.getStringExtra("availability") ?: "Άγνωστη"}"
        ticketTypes.text = "Τύποι Εισιτηρίων: ${intent.getStringExtra("ticket_types") ?: "Μη διαθέσιμο"}"
        length.text = "Διάρκεια: ${intent.getStringExtra("length") ?: "Άγνωστη"}"
        eventPromo.text = "Περιγραφή: ${intent.getStringExtra("eventPromo") ?: "Άγνωστη"}"
        organizerName.text = "Διοργανωτής: ${intent.getStringExtra("organizerName") ?: "Άγνωστη"}"
        //ticketColor.text = "Τοποθεσία: ${intent.getStringExtra("ticketColor") ?: "Άγνωστη"}"
        //price.text = "Τιμή: ${intent.getDoubleExtra("price", -1.0).takeIf { it != -1.0 }?.toString() ?: "Μη διαθέσιμη"}"

        val imageLinkUrl = intent.getStringExtra("imageLink")

        // Φόρτωση της εικόνας
        if (!imageLinkUrl.isNullOrEmpty()) {
            imageLink.load(imageLinkUrl) {
                placeholder(R.drawable.placeholder) // Placeholder εικόνα κατά τη φόρτωση
                error(R.drawable.placeholder) // Εναλλακτική εικόνα αν η φόρτωση αποτύχει
            }
        } else {
            imageLink.setImageResource(R.drawable.placeholder) // Εναλλακτική εικόνα αν δεν υπάρχει διεύθυνση
        }

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
        eventType.text = "Είδος Event: ${event.eventType ?: "Άγνωστη"}"
        name.text = event.name
        artist.text = "Καλλιτέχνης: ${event.artist ?: "Άγνωστη"}"
        //artist2.text = "Περιγραφή: ${event.artist2 ?: "Άγνωστη"}"
        //cast.text = "Περιγραφή: ${event.cast ?: "Άγνωστη"}"
        //genre.text = "Περιγραφή: ${event.genre ?: "Άγνωστη"}"
        location.text = "Πόλη: ${event.location ?: "Άγνωστη"}"
        //customId.text = "Περιγραφή: ${event.customId ?: "Άγνωστη"}"
        date.text = "Ημερομηνία: ${event.date ?: "Άγνωστη"}"
        startTime.text = "Ώρα Έναρξης: ${event.startTime ?: "Άγνωστη"}"
        venue.text = "Τοποθεσία: ${event.venue ?: "Άγνωστη"}"
        availability.text = "Διαθεσιμότητα: ${event.availability ?: "Άγνωστη"}"
        ticketTypes.text = "Τύποι Εισιτηρίων: ${event.ticketTypes ?: "Μη διαθέσιμο"}"
        length.text = "Διάρκεια: ${event.length ?: "Άγνωστη"}"
        eventPromo.text = "Περιγραφή: ${event.eventPromo ?: "Άγνωστη"}"
        organizerName.text = "Διάρκεια: ${event.organizerName ?: "Άγνωστη"}"
        //ticketColor.text = "Περιγραφή: ${event.ticketColor ?: "Άγνωστη"}"

        // Φόρτωση της εικόνας με Coil
        imageLink.load(event.imageLink) {
            placeholder(R.drawable.placeholder) // Εικόνα placeholder κατά τη φόρτωση
            error(R.drawable.placeholder) // Εναλλακτική εικόνα σε περίπτωση αποτυχίας φόρτωσης
        }

        // Εμφάνιση τιμών εισιτηρίων
        val ticketDetails = event.tickets.joinToString("\n") {
            "Τύπος: ${it.ticketName ?: "Άγνωστος"}, Τιμή: ${it.price}€"
        }
        ticketTypes.text = "Τύποι Εισιτηρίων:\n$ticketDetails"

    }

    private fun bookTicket(eventTitle: String) {
        Toast.makeText(this, "Κρατήθηκε το εισιτήριο για το: $eventTitle", Toast.LENGTH_SHORT).show()

    }
}