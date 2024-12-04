package com.example.eventapp
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FeaturedEventsAdapter(private val events: List<Event>) :
    RecyclerView.Adapter<FeaturedEventsAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.eventName)
        val dateTextView: TextView = itemView.findViewById(R.id.eventDate)
        val imageView: ImageView = itemView.findViewById(R.id.eventImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event_card, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.nameTextView.text = event.name
        holder.dateTextView.text = event.date
        holder.imageView.setImageResource(R.drawable.event) // Placeholder image
    }

    override fun getItemCount(): Int = events.size
}
