package com.example.eventapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventsAdapter(private val events: List<String>) :
    RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    // ViewHolder για κάθε στοιχείο της λίστας
    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventNameTextView: TextView = itemView.findViewById(R.id.eventName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_card, parent, false) // Σύνδεση με το XML της κάρτας
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.eventNameTextView.text = events[position] // Ρύθμιση δεδομένων
    }

    override fun getItemCount(): Int = events.size
}
