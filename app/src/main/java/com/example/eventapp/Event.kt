package com.example.eventapp

import java.io.Serializable

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
): Serializable

data class Ticket(
    val ticketType: Number,
    val ticketName: String,
    val price: Double,
    val quantity: Number,
    val remaining: Number
): Serializable

