package com.example.eventapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load


class EventsAdapter(private var events: List<Event>) :
    RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    private var onItemClickListener: ((Event) -> Unit)? = null

    // ViewHolder για κάθε στοιχείο της λίστας
    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventImage: ImageView = itemView.findViewById(R.id.eventImage)
        val eventName: TextView = itemView.findViewById(R.id.eventName)
        val eventDate: TextView = itemView.findViewById(R.id.eventDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_card, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]

        // Ορισμός δεδομένων
        holder.eventName.text = event.name
        holder.eventDate.text = "Ημερομηνία: ${event.date}"

        // Χρήση Coil για φόρτωση της εικόνας
        if (!event.imageLink.isNullOrEmpty()) {
            holder.eventImage.load(event.imageLink) {
                placeholder(R.drawable.placeholder) // placeholder κατά τη φόρτωση
                error(R.drawable.placeholder) // εικόνα σφάλματος αν αποτύχει η φόρτωση
            }
        } else {
            holder.eventImage.setImageResource(R.drawable.placeholder) // Ορίστε μια default εικόνα
        }

        // Αντίκτυπος του click event
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(event)
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun updateEvents(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (Event) -> Unit) {
        onItemClickListener = listener
    }
}