package com.example.eventapp
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class FeaturedEventsAdapter(private val events: List<Event>,
                            private val onEventClick: (Event) -> Unit // Λειτουργία για click event
) : RecyclerView.Adapter<FeaturedEventsAdapter.EventViewHolder>() {


    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.eventName)
        val dateTextView: TextView = itemView.findViewById(R.id.eventDate)
        val eventImage: ImageView = itemView.findViewById(R.id.eventImage)

        init {
            itemView.setOnClickListener {
                onEventClick(events[adapterPosition]) // Κλήση της λειτουργίας για το event που πατήθηκε
            }
        }
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
        Glide.with(holder.itemView.context)
            .load(event.imageLink)
            .into(holder.eventImage)
    }

    override fun getItemCount(): Int = events.size
}
