package com.example.eventapp

data class Event(
    val eventType: String,
    val name: String,
    val artist: String,
    val artist2: String,
    val cast: String,
    val genre: String,
    val imageLink: String,
    val location: String,
    val customId: String,
    val date: String,
    val startTime: String,
    val venue: String,
    val availability: Boolean,
    val ticketTypes: String,
    val length: String,
    val eventPromo: String,
    val organizerName: String,
    val ticketColor: String,
    val tickets: List<Ticket>

)

data class Ticket(
    val ticketType: Int? = 0,
    val ticketName: String? = null,
    val price: Double,
    val quantity: Int,
    val remaining: Int
)
