package com.example.eventapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
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
    private lateinit var availability: TextView
    private lateinit var eventType: TextView
    private lateinit var artist: TextView
    private lateinit var artist2: TextView
    private lateinit var cast: TextView
    private lateinit var genre: TextView
    private lateinit var imageLink: ImageView
    private lateinit var location: TextView
    private lateinit var startTime: TextView
    private lateinit var ticketTypes: TextView
    private lateinit var length: TextView
    private lateinit var organizerName: TextView
    private lateinit var ticketColor: TextView
    private lateinit var ticketsContainer: LinearLayout


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
        artist2 = findViewById(R.id.artist2TextView)
        cast = findViewById(R.id.castTextView)
        genre = findViewById(R.id.genreTextView)
        imageLink = findViewById(R.id.imageLinkImageView)
        location = findViewById(R.id.locationTextView)
        startTime = findViewById(R.id.startTimeTextView)
        organizerName = findViewById(R.id.organizerNameTextView)
        ticketsContainer = findViewById(R.id.ticketsContainer)





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
        name.text = intent.getStringExtra("eventName") ?: "Unknown Name"
        artist.text = "Καλλιτέχνης: ${intent.getStringExtra("artist") ?: "Άγνωστη"}"
        val artist2Value = intent.getStringExtra("artist2")?.trim() ?: ""
        artist2.text = "Καλλιτέχνης: $artist2Value"
        artist2.visibility = if (artist2Value.isEmpty()) View.GONE else View.VISIBLE
        val castValue = intent.getStringExtra("cast")?.trim() ?: ""
        cast.text = "Cast: $castValue"
        cast.visibility = if (castValue.isEmpty()) View.GONE else View.VISIBLE
        val genreValue = intent.getStringExtra("genre")?.trim() ?: ""
        genre.text = "Κατηγορία: $genreValue"
        genre.visibility = if (genreValue.isEmpty()) View.GONE else View.VISIBLE
        val locationValue = intent.getStringExtra("city")?.trim() ?: ""
        location.text = "Πόλη: $locationValue"
        location.visibility = if (locationValue.isEmpty()) View.GONE else View.VISIBLE
        date.text = "Ημερομηνία: ${intent.getStringExtra("eventDate") ?: "Άγνωστη"}"
        startTime.text = "Ώρα Έναρξης: ${intent.getStringExtra("startTime") ?: "Άγνωστη"}"
        venue.text = "Τοποθεσία: ${intent.getStringExtra("venue") ?: "Άγνωστη"}"
        val availabilityValue = intent.getStringExtra("availability")?.trim() ?: ""
        availability.text = "Διαθεσιμότητα: $availabilityValue"
        availability.visibility = if (availabilityValue.isEmpty()) View.GONE else View.VISIBLE
        ticketTypes.text = "Τύποι Εισιτηρίων: ${intent.getStringExtra("ticket_types") ?: "Μη διαθέσιμο"}"
        length.text = "Διάρκεια: ${intent.getStringExtra("length") ?: "Άγνωστη"}"
        eventPromo.text = intent.getStringExtra("eventPromo") ?: "Unknown Name"
        organizerName.text = "Διοργανωτής: ${intent.getStringExtra("organizerName") ?: "Άγνωστη"}"
        val ticketColorValue = intent.getStringExtra("ticketColor")?.trim() ?: ""


        // Βρείτε το TextView για τη φράση
        val availableTicketsTextView = findViewById<TextView>(R.id.availableTicketsTextView)

        when {
            ticketColorValue.equals("green", ignoreCase = true) -> {
                availableTicketsTextView.text = "Υπάρχουν αρκετά διαθέσιμα εισιτήρια"
                availableTicketsTextView.setTextColor(Color.parseColor("#006400"))
                availableTicketsTextView.setTypeface(null, Typeface.BOLD)// Πράσινο χρώμα
            }
            ticketColorValue.equals("orange", ignoreCase = true) -> {
                availableTicketsTextView.text = "Τα εισητήρια έχουν σχεδόν εξαντληθεί"
                availableTicketsTextView.setTextColor(Color.parseColor("#FFA500"))
                availableTicketsTextView.setTypeface(null, Typeface.BOLD)// Πορτοκαλί χρώμα
            }
            ticketColorValue.equals("red", ignoreCase = true) -> {
                availableTicketsTextView.text = "Sold Out"
                availableTicketsTextView.setTextColor(Color.RED)
                availableTicketsTextView.setTypeface(null, Typeface.BOLD)// Κόκκινο χρώμα
            }
            ticketColorValue.equals("yellow", ignoreCase = true) -> {
                availableTicketsTextView.text = "Υπάρχουν διαθέσιμα εισιτήρια"
                availableTicketsTextView.setTextColor(Color.YELLOW)
                availableTicketsTextView.setTypeface(null, Typeface.BOLD)// Κίτρινο χρώμα
            }
            else -> {
                availableTicketsTextView.text = "Υπάρχουν αρκετά διαθέσιμα εισιτήρια"
                availableTicketsTextView.setTextColor(Color.BLACK) // Επιστροφή στο κανονικό χρώμα
            }
        }


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

        // Ανάγνωση των εισιτηρίων
        val tickets = intent.getSerializableExtra("tickets") as? ArrayList<Ticket> ?: arrayListOf()
        displayTickets(tickets.toList())

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
        val artist2Value = intent.getStringExtra("artist2")?.trim() ?: ""
        artist2.text = "Καλλιτέχνης: ${event.artist2 ?: "Άγνωστη"}"
        artist2.visibility = if (artist2Value.isEmpty()) View.GONE else View.VISIBLE
        val castValue = intent.getStringExtra("cast")?.trim() ?: ""
        cast.text = "Cast: $castValue"
        cast.visibility = if (castValue.isEmpty()) View.GONE else View.VISIBLE
        val genreValue = intent.getStringExtra("genre")?.trim() ?: ""
        genre.text = "Κατηγορία: $genreValue"
        genre.visibility = if (genreValue.isEmpty()) View.GONE else View.VISIBLE
        val locationValue = intent.getStringExtra("city")?.trim() ?: ""
        location.text = "Πόλη: $locationValue"
        location.visibility = if (locationValue.isEmpty()) View.GONE else View.VISIBLE
        date.text = "Ημερομηνία: ${event.date ?: "Άγνωστη"}"
        startTime.text = "Ώρα Έναρξης: ${event.startTime ?: "Άγνωστη"}"
        venue.text = "Τοποθεσία: ${event.venue ?: "Άγνωστη"}"
        val availabilityValue = intent.getStringExtra("availability")?.trim() ?: ""
        availability.text = "Διαθεσιμότητα: $availabilityValue"
        availability.visibility = if (availabilityValue.isEmpty()) View.GONE else View.VISIBLE
        ticketTypes.text = "Τύποι Εισιτηρίων: ${event.ticketTypes ?: "Μη διαθέσιμο"}"
        length.text = "Διάρκεια: ${event.length ?: "Άγνωστη"}"
        eventPromo.text = event.eventPromo
        organizerName.text = "Διάρκεια: ${event.organizerName ?: "Άγνωστη"}"
        val ticketColorValue = intent.getStringExtra("ticketColor")?.trim() ?: ""
        ticketColor.text = "Χρώμα Εισιτηρίου: $ticketColorValue"
        ticketColor.visibility = if (ticketColorValue.isEmpty()) View.GONE else View.VISIBLE

        // Φόρτωση της εικόνας με Coil
        imageLink.load(event.imageLink) {
            placeholder(R.drawable.placeholder) // Εικόνα placeholder κατά τη φόρτωση
            error(R.drawable.placeholder) // Εναλλακτική εικόνα σε περίπτωση αποτυχίας φόρτωσης
        }

        displayTickets(event.tickets)
    }

    private fun bookTicket(eventTitle: String) {
        Toast.makeText(this, "Κρατήθηκε το εισιτήριο για το: $eventTitle", Toast.LENGTH_SHORT).show()

    }

    private fun displayTickets(tickets: List<Ticket>) {
        ticketsContainer.removeAllViews() // Καθαρισμός υπάρχοντος περιεχομένου

        for (ticket in tickets) {
            val ticketView = LayoutInflater.from(this).inflate(R.layout.item_ticket, ticketsContainer, false)

            val ticketNameTextView = ticketView.findViewById<TextView>(R.id.ticketNameTextView)
            val priceTextView = ticketView.findViewById<TextView>(R.id.priceTextView)
            val quantityTextView = ticketView.findViewById<TextView>(R.id.quantityTextView)
            val remainingTextView = ticketView.findViewById<TextView>(R.id.remainingTextView)

            ticketNameTextView.text = "Όνομα Εισιτηρίου: ${ticket.ticketName}"
            priceTextView.text = "Τιμή: ${ticket.price} €"
            quantityTextView.text = "Ποσότητα: ${ticket.quantity}"
            remainingTextView.text = "Υπόλοιπα: ${ticket.remaining}"

            ticketsContainer.addView(ticketView)
        }
    }
}