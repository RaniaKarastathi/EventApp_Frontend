package com.example.eventapp

data class Event(
    val id: String,
    val name: String,
    val description: String,
    val date: String,
    val venue: String,
    val price: Double,
    val availability: String,
    val ticketTypes: String,
    val duration: String
)